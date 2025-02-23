package com.glowmart.shop_management.converter;

import com.glowmart.shop_management.dto.OrdersItemDto;
import com.glowmart.shop_management.entity.OrdersItem;

/**
 * Utility class for converting between {@link OrdersItem} and {@link OrdersItemDto}.
 */
public class OrdersItemConverter {

    /**
     * Converts an {@link OrdersItemDto} object to an {@link OrdersItem} entity.
     *
     * @param ordersItemDto The DTO to be converted.
     * @return The corresponding {@link OrdersItem} entity.
     */
    public static OrdersItem convertToOrdersItem(OrdersItemDto ordersItemDto){
        return new OrdersItem(ordersItemDto.getCartItem(),
                ordersItemDto.getOrders(),
                ordersItemDto.getPrice(),
                ordersItemDto.getCreatedAt());
    }

    /**
     * Converts an {@link OrdersItem} entity to an {@link OrdersItemDto}.
     *
     * @param ordersItem The entity to be converted.
     * @return The corresponding {@link OrdersItemDto}.
     */
    public static OrdersItemDto convertToOrdersItemDto(OrdersItem ordersItem){
        return new OrdersItemDto(ordersItem.getCartItem(),
                ordersItem.getOrders(),
                ordersItem.getPrice(),
                ordersItem.getCreatedAt());
    }

}
