package com.example.jpatemplate.entity.query;

import com.example.jpatemplate.address.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SelectQueryTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void typedQuery_to_select_test(){
        var address1 = Address.builder().id(1L).country("USA").details("California").build();
        var address2 = Address.builder().id(2L).country("Korea").details("Seoul").build();
        var address3 = Address.builder().id(3L).country("USA").details("LA").build();

        em.persist(address1);
        em.persist(address2);
        em.persist(address3);

        em.flush();
        em.clear();

        String country = "USA";

        List<Address> addresses = em.createQuery("SELECT a FROM Address a WHERE a.country LIKE :country", Address.class)
                .setParameter("country", country)
                .getResultList();
        assertEquals(2,addresses.size());

    }

    @Test
    void criteriaQuery_to_select_test(){
        var address1 = Address.builder().id(1L).country("USA").details("California").build();
        var address2 = Address.builder().id(2L).country("Korea").details("Seoul").build();
        var address3 = Address.builder().id(3L).country("USA").details("LA").build();

        em.persist(address1);
        em.persist(address2);
        em.persist(address3);

        em.flush();
        em.clear();

        String country = "USA";

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Address> q = cb.createQuery(Address.class);
        Root<Address> address = q.from(Address.class);
        q.select(address)
                .where(cb.equal(address.get("country"),country))
                .from(Address.class);
        List<Address> addresses = em.createQuery(q)
                .getResultList();
        assertEquals(2,addresses.size());


    }
}
