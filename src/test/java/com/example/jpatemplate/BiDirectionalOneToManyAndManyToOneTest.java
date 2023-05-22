package com.example.jpatemplate;

import com.example.jpatemplate.account.Account;
import com.example.jpatemplate.user.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class BiDirectionalOneToManyAndManyToOneTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void persist_test(){
        var customer1 = Customer.builder().id(1L).customerId("abcd").build();
        var account1 = Account.builder().id(1L).balance(2.0).build();
        var account2 = Account.builder().id(2L).balance(15.0).build();
        customer1.addAccount(account1);
        customer1.addAccount(account2);
        em.persist(customer1);
        em.flush();
        em.clear();
        var customerFromContext = em.find(Customer.class, 1L);
        assertEquals(customerFromContext.getAccounts().get(0).getId(),account1.getId());
        assertEquals(customerFromContext.getAccounts().get(0).getBalance(),account1.getBalance());
        assertEquals(customerFromContext.getAccounts().get(1).getId(),account2.getId());
        assertEquals(customerFromContext.getAccounts().get(1).getBalance(),account2.getBalance());
    }




    @Test
    void when_orphanRemoval_is_true_then_remove_owner_side_when_removing_relationship_test(){
        var customer1 = Customer.builder().id(1L).customerId("abcd").build();
        var account1 = Account.builder().id(1L).balance(2.0).build();
        var account2 = Account.builder().id(2L).balance(15.0).build();
        customer1.addAccount(account1);
        customer1.addAccount(account2);
        em.persist(customer1);
        customer1.removeAccount(account1);
        em.persist(customer1);
        em.flush();
        em.clear();
        var account1FromContextNew = em.find(Account.class,1L);
        assertNull(account1FromContextNew);
    }


}
