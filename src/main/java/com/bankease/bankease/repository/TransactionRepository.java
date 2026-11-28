package com.bankease.bankease.repository;

import com.bankease.bankease.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends
        JpaRepository<Transaction, Long> {

    @Query("""
            select t from Transaction t
            left join fetch t.fromAccount
            left join fetch t.toAccount
            where t.fromAccount.id = :accountId or t.toAccount.id = :accountId
            order by t.createdAt desc            
            """)
    List<Transaction> findByAccountId(@Param("accountId") Long accountId);
}
