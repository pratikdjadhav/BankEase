package com.bankease.bankease.dto;

import com.bankease.bankease.entities.AccountType;
import jakarta.validation.constraints.NotNull;

public record AccountRequest(
        @NotNull Long customerId,
        @NotNull AccountType type
) {
}
