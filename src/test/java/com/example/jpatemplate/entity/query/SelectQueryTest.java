package com.example.jpatemplate.entity.query;

import com.example.jpatemplate.address.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SelectQueryTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void create_TypedQuery_to_select_listf(){
        var address1 = Address.builder().id(1L).country("USA").details("California").build();
        var address2 = Address.builder().id(2L).country("Korea").details("Seoul").build();
        var address3 = Address.builder().id(3L).country("USA").details("LA").build();

        em.persist(address1);
        em.persist(address2);
        em.persist(address3);

        em.flush();
        em.clear();

        String country = "USA";

        List<Address> countries = em.createQuery("SELECT a FROM Address a WHERE a.country LIKE :country", Address.class)
                .setParameter("country", country)
                .getResultList();
        assertEquals(2,countries.size());

    }
}
