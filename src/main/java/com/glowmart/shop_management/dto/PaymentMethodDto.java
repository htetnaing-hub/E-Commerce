package com.glowmart.shop_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for the Payment Method entity.
 * <p>
 * This class is used to transfer payment method-related data between different layers of the application,
 * ensuring that only the necessary details are exposed in API responses.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentMethodDto {

    /**
     * Unique identifier for the payment method.
     * <p>
     * This field represents the payment method ID, which is assigned when the payment method is created.
     * </p>
     */
    private Long paymentMethodId;

    /**
     * The name of the payment method.
     * <p>
     * This field is required and must not be empty.
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @NotBlank(message = "Payment method name is required.")
    private String paymentMethodName;

    /**
     * A brief description of the payment method.
     * <p>
     * This field provides details about the payment method and is required.
     * </p>
     */
    @NotBlank(message = "Payment method description is required.")
    private String paymentMethodDescription;

    /**
     * Timestamp indicating when the payment method was created.
     * <p>
     * This field is required and is set when the payment method is added.
     * </p>
     */
    @NotBlank(message = "Created time is required.")
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when the payment method was last updated.
     * <p>
     * This field is optional and is updated whenever any changes are made to the payment method.
     * </p>
     */
    private LocalDateTime updatedAt;

    /**
     * Timestamp indicating when the payment method was deleted.
     * <p>
     * This field is optional and is only set if the payment method has been marked as deleted.
     * </p>
     */
    private LocalDateTime deletedAt;

}
