package com.bankease.bankease.controller;

import com.bankease.bankease.dto.CustomerRequest;
import com.bankease.bankease.dto.CustomerResponse;
import com.bankease.bankease.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.create(request));
    }

    @GetMapping("/{id}")
    public CustomerResponse get(@PathVariable Long id) {
        return customerService.getById(id);
    }

    @GetMapping
    public Page<CustomerResponse> list(@PageableDefault(size = 10) Pageable pageable) {
        return customerService.getAll(pageable);
    }

    @PutMapping("/{id}")
    public CustomerResponse update(@PathVariable Long id,
                                   @Valid @RequestBody CustomerRequest request) {
        return customerService.update(id, request);

    }


}
