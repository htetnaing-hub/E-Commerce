package com.glowmart.shop_management.controller;

import com.glowmart.shop_management.api.UserAPI;
import com.glowmart.shop_management.dto.UserDto;
import com.glowmart.shop_management.exception.DuplicateException;
import com.glowmart.shop_management.exception.NotFoundException;
import com.glowmart.shop_management.exception.NotValidException;
import com.glowmart.shop_management.security.AuthResponse;
import com.glowmart.shop_management.security.CustomUserDetailsService;
import com.glowmart.shop_management.security.JwtUtil;
import com.glowmart.shop_management.security.RefreshTokenService;
import com.glowmart.shop_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST controller for managing user operations.
 * <p>
 * This controller provides endpoints for user registration, authentication,
 * token refresh, logout, and user retrieval. It delegates business logic to
 * the {@link UserService}, handles authentication via Spring Security's
 * {@link AuthenticationManager}, and manages token generation and validation
 * using {@link JwtUtil} and {@link RefreshTokenService}.
 * </p>
 *
 * <h3>Available Endpoints:</h3>
 * <ul>
 *   <li><b>POST</b> {@code /api/user/{role}/sign-up} – Register a new user with a given role.</li>
 *   <li><b>POST</b> {@code /api/user/login} – Authenticate a user and issue JWT access/refresh tokens.</li>
 *   <li><b>POST</b> {@code /api/user/refresh} – Refresh the access token using a valid refresh token.</li>
 *   <li><b>POST</b> {@code /api/user/logout} – Invalidate a refresh token to log out the user.</li>
 *   <li><b>GET</b> {@code /api/user/user-list} – Retrieve a paginated list of users using keyset pagination.</li>
 * </ul>
 *
 * <p>
 * Exception handling is performed at the controller level, returning appropriate
 * HTTP status codes such as {@code 400 Bad Request}, {@code 401 Unauthorized},
 * {@code 404 Not Found}, or {@code 409 Conflict} depending on the error.
 * </p>
 */
@RestController
@RequestMapping(UserAPI.BASE_PATH)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Create or registeration a new user.
     * @param role Role of user.
     * @param userDto Data of user.
     * @return ResponseEntity with a message indicating success or failure.
     */
    @PostMapping(UserAPI.USER_SIGN_UP)
    public ResponseEntity<?> createUser(@PathVariable("role") String role, @RequestBody UserDto userDto){
        try {
            UserDto createdUserDto = userService.createUser(role, userDto);
        } catch(Exception exception){
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User is successfully created.", HttpStatus.CREATED);
    }

    /**
     * Login a user.
     * @param email User's email.
     * @param password User's password.
     * @return A JWT token if authentication is successful.
     */
    @PostMapping(UserAPI.USER_LOGIN)
    public ResponseEntity<AuthResponse> login(@RequestParam String email, @RequestParam String password) {

        try {
            // Authenticate user credentials
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        // Load user details
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Update login time if user exists
        if (userService.userExistsByEmail(email)) {
            userService.updateLoginTime(email);
        }

        // Generate tokens
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        // Optionally store refresh token in DB or cache for logout/revocation
        refreshTokenService.storeToken(userDetails.getUsername(), refreshToken);

        // Build structured response
        AuthResponse response = new AuthResponse(
                accessToken,
                refreshToken,
                userDetails.getUsername(),
                "Login successful"
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Refresh the user's access token using a valid refresh token.
     * <p>
     * This endpoint validates the provided refresh token. If the token is valid and not expired,
     * a new access token is generated and returned to the client. The refresh token itself
     * remains unchanged and can be reused until it expires or is revoked.
     * </p>
     *
     * @param refreshToken the refresh token previously issued to the user
     * @return a response containing a new access token and the existing refresh token if valid,
     *         or an unauthorized response if the refresh token is invalid or expired
     */
    @PostMapping(UserAPI.REFRESH_TOKEN)
    public ResponseEntity<AuthResponse> refresh(@RequestParam String refreshToken) {
        if (refreshTokenService.validateToken(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            String newAccessToken = jwtUtil.generateAccessToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshToken, username, "Token refreshed"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(null, null, null, "Invalid refresh token"));
    }

    /**
     * Log out a user by invalidating their refresh token.
     * <p>
     * This endpoint revokes the provided refresh token, preventing it from being used
     * to obtain new access tokens. After logout, the user must log in again to receive
     * fresh tokens. Any existing access tokens will remain valid until they expire,
     * unless additional blacklist checks are implemented.
     * </p>
     *
     * @param refreshToken the refresh token to invalidate
     * @return a response indicating that the user has been logged out successfully
     */
    @PostMapping(UserAPI.USER_LOGOUT)
    public ResponseEntity<String> logout(@RequestParam String refreshToken) {
        refreshTokenService.invalidateToken(refreshToken);
        return ResponseEntity.ok("Logged out successfully");
    }

    /**
     * Retrieves a paginated list of users using keyset pagination.
     * Returns users whose IDs are greater than the specified lastId.
     *
     * @param lastId The last user ID from the previous page (default is 0).
     * @param size   The maximum number of users to return (default is 10).
     * @return A list of {@link UserDto} objects representing the next page of users.
     */
    @GetMapping(UserAPI.USER_LIST)
    public List<UserDto> getUsersKeyset(
            @RequestParam(defaultValue = "0") Long lastId,
            @RequestParam(defaultValue = "100") int size
    ) {
        return userService.findUsersAfterId(lastId, size);
    }

    /**
     * Updates the information of a user account identified by the given ID.
     * <p>
     * This endpoint allows an authenticated user to update their own account details, including email,
     * name, and phone number. The operation is restricted to the account owner only—authorization is
     * enforced by comparing the email in the JWT token with the email of the user retrieved from the database.
     * </p>
     *
     * @param id    the unique identifier of the user to be updated (must match the authenticated user's ID)
     * @param email the new email address to update
     * @param name  the new name to update
     * @param phone the new phone number to update
     * @return a {@link ResponseEntity} indicating the result of the update operation:
     *         <ul>
     *             <li>{@code 200 OK} if the update is successful</li>
     *             <li>{@code 403 FORBIDDEN} if the authenticated user is not the account owner</li>
     *             <li>{@code 400 BAD REQUEST} if the input data is invalid</li>
     *             <li>{@code 404 NOT FOUND} if the user does not exist</li>
     *             <li>{@code 409 CONFLICT} if the update causes a duplicate conflict</li>
     *             <li>{@code 500 INTERNAL SERVER ERROR} for unexpected errors</li>
     *         </ul>
     */
    @PutMapping(UserAPI.USER_UPDATE)
    public ResponseEntity<?> updateUserById(@PathVariable("id") String id,
                                            @RequestParam String email,
                                            @RequestParam String name,
                                            @RequestParam String phone) {
        try {
            // Get current login email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String authenticatedUserEmail = authentication.getName();

            // Get user by id from DB to compare with login email
            UserDto getUserById = userService.findUserById(id);

            // Compare login email and retrieved email from DB
            if (!authenticatedUserEmail.equals(getUserById.getUserEmail())) {
                return new ResponseEntity<>("Unauthorized: You can only update your own account", HttpStatus.FORBIDDEN);
            }

            // Update user information
            userService.updateUserById(id, email, name, phone);
            return new ResponseEntity<>("User is successfully updated", HttpStatus.OK);
        } catch (NotValidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
