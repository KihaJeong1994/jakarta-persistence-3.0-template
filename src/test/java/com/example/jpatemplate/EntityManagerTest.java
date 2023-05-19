package com.example.jpatemplate;

import com.example.jpatemplate.address.Address;
import com.example.jpatemplate.user.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class EntityManagerTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void persist_test(){
        var customer1 = new Customer();
        customer1.setId(1L);
        customer1.setCustomerId("abcd");
        em.persist(customer1);
        // persistence context is flushed to database -> insert query
        em.flush();
        // if I detach the entity manager from persistence context(em.clear()) -> em.find() select data from database. if not, search from persistence context, so no select query execute
        em.clear();
        // detach the entity instance from persistence context
        //em.detach(customer1);
        var customerFromContext = em.find(Customer.class, 1L);
        assertEquals(customer1.getCustomerId(),customerFromContext.getCustomerId());
    }

    @Test
    void cascade_test(){
        var customer1 = new Customer();
        customer1.setId(1L);
        customer1.setCustomerId("abcd");
        var address1 = new Address();
        address1.setId(1L);
        address1.setCountry("America");
        address1.setDetails("California");
        customer1.setAddress(address1);
//        em.persist(address1); // can undo this line of code by cascade option
        em.persist(customer1);
        em.flush();
        em.clear();
        var customerFromContext = em.find(Customer.class, 1L);
        assertEquals(customer1.getCustomerId(),customerFromContext.getCustomerId());
        assertEquals(address1.getId(), customerFromContext.getAddress().getId());
    }
}
