package com.glowmart.shop_management.dto;

import com.glowmart.shop_management.entity.CartItem;
import com.glowmart.shop_management.entity.Orders;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for the OrderItem entity.
 * <p>
 * This class is used to transfer order item-related data between different layers of the application,
 * ensuring that only the necessary details are exposed in API responses.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrdersItemDto {

    /**
     * The cart item associated with the order item.
     * <p>
     * This field represents the product and quantity selected by the user before placing an order.
     * </p>
     */
    private CartItem cartItem;

    /**
     * The order to which this order item belongs.
     * <p>
     * This field establishes a relationship between the order item and the order it is part of.
     * </p>
     */
    private Orders orders;

    /**
     * The price of the order item.
     * <p>
     * This field represents the price of the item.
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @NotBlank(message = "Price is required.")
    private double price;

    /**
     * Timestamp indicating when the order item was created.
     * <p>
     * This field is required and is set when the order item is added.
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @NotBlank(message = "Created time is required.")
    private LocalDateTime createdAt;

}
