package com.bankease.bankease.dto;

import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String name,
        String phone,
        String email,
        String address,
        LocalDateTime createdAt
) {
}
