package com.glowmart.shop_management.converter;

import com.glowmart.shop_management.dto.CartDto;
import com.glowmart.shop_management.entity.Cart;

/**
 * A utility class that provides conversion methods between {@link Cart} and {@link CartDto}.
 * <p>
 * This class helps in transforming entity objects to DTOs and vice versa,
 * ensuring a clean separation between database entities and data transfer objects.
 * </p>
 */
public class CartConverter {

    /**
     * Converts a {@link CartDto} object to a {@link Cart} entity.
     *
     * @param cartDto the CartDto object to convert
     * @return a new Cart entity with values copied from the provided DTO
     */
    public static Cart convertToCart(CartDto cartDto){
        return new Cart(cartDto.getCartId(),
                cartDto.getUser(),
                cartDto.getTotalPrice(),
                cartDto.getStatus(),
                cartDto.getCreatedAt(),
                cartDto.getUpdatedAt());
    }

    /**
     * Converts a {@link Cart} entity to a {@link CartDto} object.
     *
     * @param cart the Cart entity to convert
     * @return a new CartDto object with values copied from the provided entity
     */
    public static CartDto convertToCartDto(Cart cart){
        return new CartDto(cart.getCartId(),
                cart.getUser(),
                cart.getTotalPrice(),
                cart.getStatus(),
                cart.getCreatedAt(),
                cart.getUpdatedAt());
    }

}
