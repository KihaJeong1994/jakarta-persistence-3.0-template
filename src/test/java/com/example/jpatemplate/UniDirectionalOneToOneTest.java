package com.example.jpatemplate;

import com.example.jpatemplate.address.Address;
import com.example.jpatemplate.user.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class UniDirectionalOneToOneTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void when_cascade_persist_option_then_persist_inverse_side_when_persisting_owning_side_test(){
        var customer1 = Customer.builder().id(1L).customerId("abcd").build();
        var address1 = Address.builder().id(1L).country("America").details("California").build();
        customer1.setAddress(address1);
//        em.persist(address1); // can undo this line of code by cascade option
        em.persist(customer1);
        em.flush();
        em.clear();
        var customerFromContext = em.find(Customer.class, 1L);
        assertEquals(customer1.getCustomerId(),customerFromContext.getCustomerId());
        assertEquals(address1.getId(), customerFromContext.getAddress().getId());
    }

    @Test
    void when_orphanRemoval_is_true_then_remove_child_side_when_removing_parent_side_test(){
        var customer1 = Customer.builder().id(1L).customerId("abcd").build();
        var address1 = Address.builder().id(1L).country("America").details("California").build();
        customer1.setAddress(address1);
        em.persist(customer1);
        em.remove(customer1);
        em.flush(); // orphanRemoval is applied at the time of flush operation
        em.clear();
        var addressFromContext = em.find(Address.class,1L);
        assertNull(addressFromContext);
    }

    @Test
    void when_orphanRemoval_is_true_then_remove_child_side_when_relation_has_changed_test(){
        var customer1 = Customer.builder().id(1L).customerId("abcd").build();
        var address1 = Address.builder().id(1L).country("America").details("California").build();
        customer1.setAddress(address1);
        em.persist(customer1);
        var address2 = Address.builder().id(2L).country("America").details("California").build();
        customer1.setAddress(address2);
        em.persist(customer1);
        em.flush(); // orphanRemoval is applied at the time of flush operation
        em.clear();
        var addressFromContext = em.find(Address.class,1L);
        assertNull(addressFromContext);
    }

}
