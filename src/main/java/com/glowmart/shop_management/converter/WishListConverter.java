package com.glowmart.shop_management.converter;

import com.glowmart.shop_management.dto.WishListDto;
import com.glowmart.shop_management.entity.WishList;

/**
 * Utility class for converting between {@link WishList} and {@link WishListDto}.
 */
public class WishListConverter {

    /**
     * Converts a {@link WishListDto} object to a {@link WishList} entity.
     *
     * @param wishListDto The DTO to be converted.
     * @return The corresponding {@link WishList} entity.
     */
    public static WishList convertToWishList(WishListDto wishListDto){
        return new WishList(wishListDto.getWishListId(),
                wishListDto.getUser(),
                wishListDto.getProducts(),
                wishListDto.getCreatedAt());
    }

    /**
     * Converts a {@link WishList} entity to a {@link WishListDto}.
     *
     * @param wishList The entity to be converted.
     * @return The corresponding {@link WishListDto}.
     */
    public static WishListDto convertToWishListDto(WishList wishList){
        return new WishListDto(wishList.getWishListId(),
                wishList.getUser(),
                wishList.getProducts(),
                wishList.getCreatedAt());
    }

}
