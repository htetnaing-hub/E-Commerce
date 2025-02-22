package com.glowmart.shop_management.converter;

import com.glowmart.shop_management.dto.CartItemDto;
import com.glowmart.shop_management.entity.CartItem;

/**
 * Utility class for converting between {@link CartItem} and {@link CartItemDto}.
 */
public class CartItemConverter {

    /**
     * Converts a {@link CartItemDto} object to a {@link CartItem} entity.
     *
     * @param cartItemDto The DTO to be converted.
     * @return The corresponding {@link CartItem} entity.
     */
    public static CartItem convertToCartItem(CartItemDto cartItemDto){
        return new CartItem(cartItemDto.getProduct(),
                cartItemDto.getCart(),
                cartItemDto.getPrice());
    }

    /**
     * Converts a {@link CartItem} entity to a {@link CartItemDto}.
     *
     * @param cartItem The entity to be converted.
     * @return The corresponding {@link CartItemDto}.
     */
    public static CartItemDto convertToCartItemDto(CartItem cartItem){
        return new CartItemDto(cartItem.getProduct(),
                cartItem.getCart(),
                cartItem.getPrice());
    }

}
