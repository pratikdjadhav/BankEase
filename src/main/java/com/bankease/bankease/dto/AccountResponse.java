package com.bankease.bankease.dto;

import com.bankease.bankease.entities.AccountStatus;
import com.bankease.bankease.entities.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String accountNumber,
        Long customerId,
        AccountType type,
        BigDecimal balance,
        AccountStatus status,
        LocalDateTime createdAt
) {
}
