package com.glowmart.shop_management.dto;

import com.glowmart.shop_management.entity.Product;
import com.glowmart.shop_management.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data Transfer Object (DTO) for the WishList entity.
 * <p>
 * This class is used to transfer wishlist-related data between different layers of the application,
 * ensuring that only the necessary details are exposed in API responses.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WishListDto {

    /**
     * Unique identifier for the wishlist.
     * <p>
     * This field represents the wishlist ID, which is assigned when the wishlist is created.
     * </p>
     */
    private Long wishListId;

    /**
     * The user who owns the wishlist.
     * <p>
     * This field establishes a relationship between the wishlist and the user who created it.
     * </p>
     */
    private User user;

    /**
     * The set of products added to the wishlist.
     * <p>
     * This field contains a collection of products that the user has saved for future reference.
     * </p>
     */
    private Set<Product> products;

    /**
     * Timestamp indicating when the wishlist was created.
     * <p>
     * This field is required and is set when the wishlist is added.
     * </p>
     */
    @NotBlank(message = "Created time is required.")
    private LocalDateTime createdAt;
}
