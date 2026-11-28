package com.bankease.bankease.controller;

import com.bankease.bankease.dto.AmountRequest;
import com.bankease.bankease.dto.TransactionResponse;
import com.bankease.bankease.dto.TransferRequest;
import com.bankease.bankease.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service){
        this.service = service;
    }

    @PostMapping("/accounts/{id}/deposit")
    public TransactionResponse deposit(@PathVariable Long id, @Valid @RequestBody AmountRequest request){
        return service.deposit(id,request.amount());
    }

    @PostMapping("/accounts/{id}/withdraw")
    public TransactionResponse withdraw(@PathVariable Long id, @Valid @RequestBody AmountRequest request){
        return service.withdraw(id,request.amount());
    }

    @PostMapping("/transfer")
    public TransactionResponse transfer(@Valid @RequestBody TransferRequest request){
        return service.transfer(request.fromAccountId(), request.toAccountId() ,request.amount());
    }

    @GetMapping("/accounts/{id}/transactions")
    public List<TransactionResponse> history(@PathVariable Long id){
        return service.history(id);
    }





}
