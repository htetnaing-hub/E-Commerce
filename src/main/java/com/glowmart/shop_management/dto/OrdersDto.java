package com.glowmart.shop_management.dto;

import com.glowmart.shop_management.entity.PaymentMethod;
import com.glowmart.shop_management.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for the Orders entity.
 * <p>
 * This class is used to transfer order-related data between different layers of the application,
 * ensuring that only the necessary details are exposed in API responses.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrdersDto {

    /**
     * Unique identifier for the order.
     * <p>
     * This field represents the order ID, which is assigned when the order is created.
     * </p>
     */
    private Long orderId;

    /**
     * The user who placed the order.
     * <p>
     * This field establishes a relationship between the order and the user who made the purchase.
     * </p>
     */
    private User user;

    /**
     * The payment method used for the order.
     * <p>
     * This field represents the payment method chosen by the user.
     * </p>
     */
    private PaymentMethod paymentMethod;

    /**
     * The total price of the order.
     * <p>
     * This field is required and must be a positive value.
     * </p>
     */
    @NotBlank(message = "Total price is required.")
    private double totalPrice;

    /**
     * The status of the order.
     * <p>
     * This field indicates the current state of the order (e.g., pending, completed, canceled).
     * </p>
     */
    @NotBlank(message = "Status is required.")
    private String status;

    /**
     * The quantity of items in the order.
     * <p>
     * This field is required and must be at least 0.
     * </p>
     *
     * @see jakarta.validation.constraints.Min
     */
    @NotBlank(message = "Quantity is required.")
    @Min(value = 0, message = "Quantity must be at least 0")
    private int quantity;

    /**
     * Timestamp indicating when the order was created.
     * <p>
     * This field is required and is set when the order is placed.
     * </p>
     */
    @NotBlank(message = "Created time is required.")
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when the order was last updated.
     * <p>
     * This field is optional and is updated whenever any changes are made to the order.
     * </p>
     */
    private LocalDateTime updatedAt;

}
