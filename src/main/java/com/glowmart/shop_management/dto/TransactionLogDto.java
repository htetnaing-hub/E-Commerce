package com.glowmart.shop_management.dto;

import com.glowmart.shop_management.entity.Orders;
import com.glowmart.shop_management.entity.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for the TransactionLog entity.
 * <p>
 * This class is used to transfer transaction log-related data between different layers of the application,
 * ensuring that only the necessary details are exposed in API responses.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionLogDto {

    /**
     * Unique identifier for the transaction log.
     * <p>
     * This field represents the transaction log ID, which is assigned when a transaction is logged.
     * </p>
     */
    private Long transactionLogId;

    /**
     * The order associated with the transaction.
     * <p>
     * This field represents the order for which the transaction was processed.
     * </p>
     */
    private Orders order;

    /**
     * The payment method used for the transaction.
     * <p>
     * This field represents the method of payment, such as credit card, PayPal, or bank transfer.
     * </p>
     */
    private PaymentMethod paymentMethod;

    /**
     * The status of the transaction.
     * <p>
     * This field indicates the current state of the transaction, such as pending, completed, or failed.
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @NotBlank(message = "Status is required.")
    private Long status;

    /**
     * Timestamp indicating when the transaction was created.
     * <p>
     * This field is required and is automatically set when the transaction is logged.
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @NotBlank(message = "Created time is required.")
    private LocalDateTime createdAt;

    /**
     * The total amount involved in the transaction.
     * <p>
     * This field represents the monetary value of the transaction.
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @NotBlank(message = "Amount is required.")
    private double amount;

}
