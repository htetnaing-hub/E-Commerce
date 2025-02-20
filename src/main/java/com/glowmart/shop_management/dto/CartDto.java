package com.glowmart.shop_management.dto;

import com.glowmart.shop_management.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for the Cart entity.
 * <p>
 * This class is used to transfer cart-related data between different layers of the application,
 * ensuring that only the necessary details are exposed in API responses.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartDto {

    /**
     * Unique identifier for the cart.
     * <p>
     * This field represents the cart ID, which is assigned when the cart is created.
     * </p>
     */
    private Long cartId;

    /**
     * The user associated with the cart.
     * <p>
     * This field establishes a relationship between the cart and the user who owns it.
     * </p>
     */
    private User user;

    /**
     * The total price of items in the cart.
     * <p>
     * This value represents the total cost of all products added to the cart.
     * </p>
     */
    private double totalPrice;

    /**
     * The status of the cart.
     * <p>
     * This field indicates the current state of the cart, such as "PENDING", "CHECKOUT", "ORDERED", "CANCELLED", or "EXPIRED".
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @NotBlank(message = "Status is required!")
    private String status;

    /**
     * Timestamp for when the cart was created.
     * <p>
     * This field is automatically set when the cart is created and cannot be updated.
     * </p>
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp for when the cart was last updated.
     * <p>
     * This field is optional and is set when the cart entity is updated.
     * </p>
     */
    private LocalDateTime updatedAt;
}
