package com.example.jpatemplate.domain.customer.repository.dsl;

import com.example.jpatemplate.domain.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerRepositoryCustom {
    Page<Customer> findByAddressCountryAndAddressDetails(String country, String details, Pageable pageable);
}
