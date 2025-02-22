package com.glowmart.shop_management.dto;

import com.glowmart.shop_management.entity.Cart;
import com.glowmart.shop_management.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Data Transfer Object (DTO) for the CartItem entity.
 * <p>
 * This class is used to transfer cart item-related data between different layers of the application,
 * ensuring that only the necessary details are exposed in API responses.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItemDto {

    /**
     * The product associated with this cart item.
     * <p>
     * This field represents the product that has been added to the cart.
     * It establishes a relationship between a cart item and its corresponding product.
     * </p>
     */
    private Product product;

    /**
     * The cart to which this item belongs.
     * <p>
     * This field represents the shopping cart that contains the item.
     * It ensures that a cart item is linked to a specific cart.
     * </p>
     */
    private Cart cart;

    /**
     * The price of the cart item.
     * <p>
     * This value represents the cost of the product at the time it was added to the cart.
     * It is required and must be a non-negative value.
     * </p>
     *
     * @see jakarta.validation.constraints.NotNull
     * @see jakarta.validation.constraints.Min
     */
    @NotBlank(message = "Price is required")
    @Min(value = 0, message = "Price must be at least 0")
    private double price;
}
