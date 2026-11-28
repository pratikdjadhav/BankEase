package com.bankease.bankease.dto;

import com.bankease.bankease.entities.Transaction;
import com.bankease.bankease.entities.TransactionStatus;
import com.bankease.bankease.entities.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse (

        String txnRef,
        TransactionType type,
        TransactionStatus status,
        BigDecimal amount,
        Long fromAccountId,
        Long toAccountId,
        String description,
        LocalDateTime createdAt
){
    public static TransactionResponse from(Transaction t){
        return new TransactionResponse(
                t.getTxnRef(),
                t.getType(),
                t.getStatus(),
                t.getAmount(),
                t.getFromAccount() !=  null ? t.getFromAccount().getId() : null,
                t.getToAccount() != null ? t.getToAccount().getId() : null,
                t.getDescription(),
                t.getCreatedAt()
        );
    }

}
