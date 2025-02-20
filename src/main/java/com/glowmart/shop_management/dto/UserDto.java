package com.glowmart.shop_management.dto;

import com.glowmart.shop_management.entity.Cart;
import com.glowmart.shop_management.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data transfer object (DTO) for a User.
 * <p>
 * This DTO is used for transferring user data between layers of the application. It
 * includes necessary validation constraints for user inputs, such as name, email, phone,
 * and password. It does not contain sensitive information like hashed passwords.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

    /**
     * Unique identifier for the user.
     * <p>
     * This field represents the user ID, typically used to reference a specific user.
     * </p>
     */
    private Long userId;

    /**
     * The role assigned to the user.
     * <p>
     * This field represents the user's role, typically an association with a predefined
     * set of permissions.
     * </p>
     */
    private Role role;

    /**
     * List of carts associated with the user.
     * <p>
     * This field contains all carts owned by the user. It represents a one-to-many
     * relationship where a user can have multiple carts.
     * </p>
     */
    private Set<Cart> cartList;

    /**
     * The username chosen by the user.
     * <p>
     * This field is required and must be between 2 and 50 characters in length.
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     * @see jakarta.validation.constraints.Size
     */
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be at lease 2 characters")
    private String userName;

    /**
     * The email address of the user.
     * <p>
     * This field is required and must follow a valid email format.
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     * @see jakarta.validation.constraints.Email
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String userEmail;

    /**
     * The phone number of the user.
     * <p>
     * This field is required and must not be blank.
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @NotBlank(message = "Phone number is required")
    private String userPhone;

    /**
     * The password for the user.
     * <p>
     * This field is required and must not be blank. It is assumed to be securely handled
     * (e.g., hashed) during the registration or login process.
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @NotBlank(message = "Password is required")
    private String userPassword;

    /**
     * Timestamp for when the user account was created.
     * <p>
     * This field represents the creation date and time of the user. It is typically
     * set automatically when the user is created in the system.
     * </p>
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp for the last update to the user account.
     * <p>
     * This field represents the last update date and time of the user account. It may
     * be updated when the user updates their profile or other details.
     * </p>
     */
    private LocalDateTime updatedAt;

    /**
     * Timestamp for the user's last login time.
     * <p>
     * This field tracks the time of the user's most recent login. It can be updated
     * whenever the user logs into the system.
     * </p>
     */
    private LocalDateTime lastLoginTime;
}
