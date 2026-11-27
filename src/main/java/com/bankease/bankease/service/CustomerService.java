package com.bankease.bankease.service;

import com.bankease.bankease.dto.CustomerRequest;
import com.bankease.bankease.dto.CustomerResponse;
import com.bankease.bankease.entities.Customer;
import com.bankease.bankease.exceptions.DuplicateResourceException;
import com.bankease.bankease.exceptions.ResourceNotFoundException;
import com.bankease.bankease.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse create(CustomerRequest request) {
        if (customerRepository.existsByPhone(request.phone()))
            throw new DuplicateResourceException("Phone Already Registered");
        if (customerRepository.existsByEmail(request.email()))
            throw new DuplicateResourceException("Email Already Registered");

        Customer customer = new Customer();
        apply(customer, request);
        return toResponse(customerRepository.save(customer));

    }

    public CustomerResponse getById(Long id) {
        return toResponse(find(id));
    }

    public CustomerResponse update(Long id, CustomerRequest request) {

        Customer customer = new Customer();

        if (!customer.getPhone().equals(request.phone())
                && customerRepository.existsByPhone(request.phone())) //checks user phone and given number not same and customer already exists
            throw new DuplicateResourceException("Phone Already Exist !!"); // only useful when user exist and values are different
        if (!customer.getEmail().equals(request.email())
                && customerRepository.existsByEmail(request.email()))//checks user email and given email not same and customer already exists
            throw new DuplicateResourceException("Email Already Exist"); // only useful when user exist and values are different


        apply(customer, request);

        return toResponse(customerRepository.save(customer));
    }


    public Page<CustomerResponse> getAll(Pageable pageable) {
        return customerRepository
                .findAll(pageable)
                .map(this::toResponse);
    }

    private Customer find(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not Found : " + id));
    }

    private void apply(Customer c, CustomerRequest r) {
        c.setName(r.name());
        c.setEmail(r.email());
        c.setPhone(r.phone());
        c.setAddress(r.address());
    }

    private CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(
                c.getId(), c.getName(), c.getPhone(), c.getEmail(), c.getAddress(), c.getCreatedAt()
        );
    }


}
