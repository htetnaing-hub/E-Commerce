package com.glowmart.shop_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents an item in a shopping cart.
 * <p>
 * This entity links a specific product to a cart, along with its price.
 * It establishes relationships between the {@link Product} and {@link Cart} entities.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class CartItem {

    /**
     * The product associated with this cart item.
     * <p>
     * This field establishes a one-to-one relationship between the cart item and the product.
     * The {@code @MapsId} annotation ensures that the product ID is also used as the cart item ID.
     * </p>
     */
    @Id
    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * The cart to which this item belongs.
     * <p>
     * This field establishes a many-to-one relationship with the {@link Cart} entity.
     * The {@code @JsonIgnore} annotation prevents circular references during JSON serialization.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonIgnore
    private Cart cart;

    /**
     * The price of the product at the time it was added to the cart.
     * <p>
     * This value represents the cost of the product when added to the cart, which may differ
     * from the current product price due to discounts or price updates.
     * </p>
     */
    @Column(nullable = false)
    private double price;

}
