package com.bankease.bankease.controller;

import com.bankease.bankease.dto.AccountRequest;
import com.bankease.bankease.dto.AccountResponse;
import com.bankease.bankease.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> open(@Valid @RequestBody AccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.open(request));

    }

    @GetMapping("/accounts/{accountNumber}")
    public AccountResponse get(@PathVariable String accountNumber) {
        return accountService.getByNumber(accountNumber);

    }

    @GetMapping("/customers/{customerId}/accounts")
    public List<AccountResponse> byCustomer(@PathVariable Long customerId) {
        return accountService.getByCustomer(customerId);
    }

}
