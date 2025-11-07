package com.glowmart.shop_management.controller;

import com.glowmart.shop_management.api.UserAPI;
import com.glowmart.shop_management.dto.UserDto;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controller for managing users.
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

}
