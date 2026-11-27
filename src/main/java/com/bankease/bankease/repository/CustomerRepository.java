package com.bankease.bankease.repository;

import com.bankease.bankease.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends
        JpaRepository<Customer, Long> {

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);
}
