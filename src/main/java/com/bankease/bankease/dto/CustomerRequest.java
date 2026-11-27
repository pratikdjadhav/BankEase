package com.bankease.bankease.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digit") String phone,
        @NotBlank @Email String email,
        String address

) {
}
