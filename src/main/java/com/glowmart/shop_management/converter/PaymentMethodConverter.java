package com.glowmart.shop_management.converter;

import com.glowmart.shop_management.dto.PaymentMethodDto;
import com.glowmart.shop_management.entity.PaymentMethod;

/**
 * Utility class for converting between {@link PaymentMethod} and {@link PaymentMethodDto}.
 */
public class PaymentMethodConverter {

    /**
     * Converts a {@link PaymentMethodDto} object to a {@link PaymentMethod} entity.
     *
     * @param paymentMethodDto The DTO to be converted.
     * @return The corresponding {@link PaymentMethod} entity.
     */
    public static PaymentMethod convertToPaymentMethod(PaymentMethodDto paymentMethodDto){
        return new PaymentMethod(paymentMethodDto.getPaymentMethodId(),
                paymentMethodDto.getPaymentMethodName(),
                paymentMethodDto.getPaymentMethodDescription(),
                paymentMethodDto.getCreatedAt(),
                paymentMethodDto.getUpdatedAt(),
                paymentMethodDto.getDeletedAt());
    }

    /**
     * Converts a {@link PaymentMethod} entity to a {@link PaymentMethodDto}.
     *
     * @param paymentMethod The entity to be converted.
     * @return The corresponding {@link PaymentMethodDto}.
     */
    public static PaymentMethodDto convertToPaymentMethodDto(PaymentMethod paymentMethod){
        return new PaymentMethodDto(paymentMethod.getPaymentMethodId(),
                paymentMethod.getPaymentMethodName(),
                paymentMethod.getPaymentMethodDescription(),
                paymentMethod.getCreatedAt(),
                paymentMethod.getUpdatedAt(),
                paymentMethod.getDeletedAt());
    }

}
