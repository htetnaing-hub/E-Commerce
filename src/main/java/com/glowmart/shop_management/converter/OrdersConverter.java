package com.glowmart.shop_management.converter;

import com.glowmart.shop_management.dto.OrdersDto;
import com.glowmart.shop_management.entity.Orders;

/**
 * Utility class for converting between {@link Orders} and {@link OrdersDto}.
 */
public class OrdersConverter {

    /**
     * Converts an {@link OrdersDto} object to an {@link Orders} entity.
     *
     * @param ordersDto The DTO to be converted.
     * @return The corresponding {@link Orders} entity.
     */
    public static Orders convertToOrders(OrdersDto ordersDto){
        return new Orders(ordersDto.getOrderId(),
                ordersDto.getUser(),
                ordersDto.getPaymentMethod(),
                ordersDto.getTotalPrice(),
                ordersDto.getStatus(),
                ordersDto.getQuantity(),
                ordersDto.getCreatedAt(),
                ordersDto.getUpdatedAt());
    }

    /**
     * Converts an {@link Orders} entity to an {@link OrdersDto}.
     *
     * @param orders The entity to be converted.
     * @return The corresponding {@link OrdersDto}.
     */
    public static OrdersDto convertToOrdersDto(Orders orders){
        return new OrdersDto(orders.getOrderId(),
                orders.getUser(),
                orders.getPaymentMethod(),
                orders.getTotalPrice(),
                orders.getStatus(),
                orders.getQuantity(),
                orders.getCreatedAt(),
                orders.getUpdatedAt());
    }

}
