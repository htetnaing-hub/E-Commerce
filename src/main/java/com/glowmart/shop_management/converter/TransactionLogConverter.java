package com.glowmart.shop_management.converter;

import com.glowmart.shop_management.dto.TransactionLogDto;
import com.glowmart.shop_management.entity.TransactionLog;

/**
 * Utility class for converting between {@link TransactionLog} and {@link TransactionLogDto}.
 */
public class TransactionLogConverter {

    /**
     * Converts a {@link TransactionLogDto} object to a {@link TransactionLog} entity.
     *
     * @param transactionLogDto The DTO to be converted.
     * @return The corresponding {@link TransactionLog} entity.
     */
    public static TransactionLog convertToTransactionLog(TransactionLogDto transactionLogDto){
        return new TransactionLog(transactionLogDto.getTransactionLogId(),
                transactionLogDto.getOrder(),
                transactionLogDto.getPaymentMethod(),
                transactionLogDto.getStatus(),
                transactionLogDto.getCreatedAt(),
                transactionLogDto.getAmount());
    }

    /**
     * Converts a {@link TransactionLog} entity to a {@link TransactionLogDto}.
     *
     * @param transactionLog The entity to be converted.
     * @return The corresponding {@link TransactionLogDto}.
     */
    public static TransactionLogDto convertToTransactionLogDto(TransactionLog transactionLog){
        return new TransactionLogDto(transactionLog.getTransactionLogId(),
                transactionLog.getOrder(),
                transactionLog.getPaymentMethod(),
                transactionLog.getStatus(),
                transactionLog.getCreatedAt(),
                transactionLog.getAmount());
    }

}
