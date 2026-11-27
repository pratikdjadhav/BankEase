package com.bankease.bankease.repository;

import com.bankease.bankease.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends
        JpaRepository<Transaction, Long> {
}
