package com.glowmart.shop_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Represents a wishlist in the shopping system.
 * <p>
 * This entity stores information about a user's wishlist, including the user who owns it
 * and the products added to the wishlist. The wishlist allows users to save products for future purchases.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class WishList {

    /**
     * Unique identifier for the wishlist.
     * <p>
     * This field is automatically generated when a new wishlist is created.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishListId;

    /**
     * The user who owns the wishlist.
     * <p>
     * This field establishes a many-to-one relationship between the wishlist and the user.
     * A user can have multiple wishlists.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    /**
     * The set of products added to the wishlist.
     * <p>
     * This field represents a many-to-many relationship between the wishlist and products.
     * A wishlist can contain multiple products, and a product can appear in multiple wishlists.
     * </p>
     */
    @ManyToMany
    @JoinTable(
            name = "wishlist_product",
            joinColumns = @JoinColumn(name = "wish_list_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;

    /**
     * Timestamp indicating when the wishlist was created.
     * <p>
     * This field is automatically set when the wishlist is created and cannot be updated.
     * </p>
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

}
