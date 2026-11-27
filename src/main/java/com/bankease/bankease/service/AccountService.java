package com.bankease.bankease.service;

import com.bankease.bankease.dto.AccountRequest;
import com.bankease.bankease.dto.AccountResponse;
import com.bankease.bankease.entities.Account;
import com.bankease.bankease.entities.Customer;
import com.bankease.bankease.exceptions.ResourceNotFoundException;
import com.bankease.bankease.repository.AccountRepository;
import com.bankease.bankease.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountResponse open(AccountRequest request) {

        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not Found !!"));

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setCustomer(customer);
        account.setType(request.type());

        return toResponse(accountRepository.save(account));

    }

    public AccountResponse getByNumber(String accountNumber) {
        return toResponse(accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account Not Found !!" + accountNumber)));
    }


    public List<AccountResponse> getByCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId))
            throw new ResourceNotFoundException("Customer Not Found !!");

        return accountRepository.findByCustomerId(customerId)
                .stream().map(this::toResponse).toList();
    }

    public String generateAccountNumber() {
        String number;
        do {
            number = "ACC" + ThreadLocalRandom.current()
                    .nextLong(1_000_000_000L, 10_000_000_000L);
        } while (accountRepository.existsByAccountNumber(number));

        return number;
    }

    public AccountResponse toResponse(Account account) {
        return new AccountResponse(account.getId(), account.getAccountNumber(), account.getCustomer().getId()
                , account.getType(), account.getBalance(), account.getStatus(), account.getCreatedAt());
    }


}
