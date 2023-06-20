package com.example.jpatemplate.customer.repository;

import com.example.jpatemplate.common.jpa.annotation.RepositoryTest;
import com.example.jpatemplate.common.jpa.querydsl.TestQueryDslConfig;
import com.example.jpatemplate.domain.address.entity.Address;
import com.example.jpatemplate.domain.customer.entity.Customer;
import com.example.jpatemplate.domain.customer.repository.CustomerRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest //auto rollback
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // add if you want to use your own database config, not embedded one
//@Import(TestQueryDslConfig.class)
@RepositoryTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private List<Customer> customerDataSet = Arrays.asList(
            Customer.builder().id(1L).customerId("david@naver.com")
                    .address(Address.builder().id(1L).country("Korea").details("Seoul").build())
                    .build()
            ,Customer.builder().id(2L).customerId("susam@yahoo.com")
                    .address(Address.builder().id(2L).country("USA").details("California").build())
                    .build()
            ,Customer.builder().id(3L).customerId("steve@apple.com")
                    .address(Address.builder().id(3L).country("China").details("Beijing").build())
                    .build()
            ,Customer.builder().id(4L).customerId("tesla@apple.com")
                    .address(Address.builder().id(4L).country("Korea").details("Busan").build())
                    .build()
    );


    @BeforeEach
    void setUp() {
        customerRepository.saveAll(customerDataSet);
    }

    @Test
    void findByAddressCountryAndAddressDetails_when_details_null_then_select_only_by_country(){
        String country = "kor";
        String details = null;
        Pageable pageable = PageRequest.of(0,5);
        Page<Customer> customers = customerRepository.findByAddressCountryAndAddressDetails(country, details, pageable);
        assertEquals(2,customers.getContent().size());
    }

    @Test
    void validation_test_customerId_should_be_email(){
        Customer customer = Customer.builder().id(5L).customerId("notEmail")
                .address(Address.builder().id(5L).country("Korea").details("Seoul").build())
                .build();
        assertThrows(ConstraintViolationException.class,()->customerRepository.saveAndFlush(customer)); // JPA validation works when flushed
    }
}