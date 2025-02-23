package com.glowmart.shop_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Represents an item in an order within the shopping system.
 * <p>
 * This entity stores details about individual items within an order, including the associated product,
 * the order it belongs to, the price, quantity, and timestamps for creation.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class OrdersItem {

    /**
     * The product associated with this order item.
     * <p>
     * This field establishes a relationship between the order item and the product in the cart.
     * </p>
     */
    @Id
    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private CartItem cartItem;

    /**
     * The order to which this item belongs.
     * <p>
     * This field links the order item to a specific order.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Orders orders;

    /**
     * The price of the order item.
     * <p>
     * This field represents the cost of the item at the time of purchase.
     * </p>
     */
    @Column(nullable = false)
    private double price;

    /**
     * Timestamp indicating when the order item was created.
     * <p>
     * This field is automatically set when the order item is created and cannot be updated.
     * </p>
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

}
