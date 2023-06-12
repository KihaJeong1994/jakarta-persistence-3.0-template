package com.example.jpatemplate.domain.customer.repository;

import com.example.jpatemplate.domain.customer.entity.Customer;
import com.example.jpatemplate.domain.customer.repository.dsl.CustomerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long>, CustomerRepositoryCustom {
}
