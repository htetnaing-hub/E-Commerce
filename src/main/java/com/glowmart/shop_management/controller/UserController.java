package com.glowmart.shop_management.controller;

import com.glowmart.shop_management.api.UserAPI;
import com.glowmart.shop_management.dto.UserDto;
import com.glowmart.shop_management.security.CustomUserDetailsService;
import com.glowmart.shop_management.security.JwtUtil;
import com.glowmart.shop_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
    public String login(@RequestParam String email, @RequestParam String password) {

        System.out.println("Email: " + email);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if (userService.userExistsByEmail(email)) {
            userService.updateLoginTime(email);
        }

        return jwtUtil.generateToken(userDetails);
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
