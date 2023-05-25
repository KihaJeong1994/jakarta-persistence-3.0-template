package com.example.jpatemplate.entity.lifecycle;

import com.example.jpatemplate.address.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class EntityLifeCycleTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void managed_entity_sync_to_db_test(){
        var address1 = Address.builder().id(1L).country("USA").details("California").build();
        em.persist(address1);
        // persistence context is flushed to database -> insert query
        em.flush();
        // if I detach the entity manager from persistence context(em.clear()) -> em.find() select data from database. if not, search from persistence context, so no select query execute
        em.clear();
        // detach the entity instance from persistence context
        //em.detach(customer1);
        var addressFromContext = em.find(Address.class, 1L);
        assertEquals(address1.getCountry(),addressFromContext.getCountry());
        assertEquals(address1.getDetails(),addressFromContext.getDetails());
    }

}
