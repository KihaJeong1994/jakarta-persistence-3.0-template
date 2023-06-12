package com.example.jpatemplate.common.jpa.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import org.springframework.data.domain.Persistable;

/*
* A base class for entities with manually assigned identifiers
* We need this class because of Spring Data JPA
* Spring Data JPA inspects the identifier by default, so if we manually assign identifier to entity, Spring Data JPA searches entity from db automatically, and this causes error
* */
@MappedSuperclass
public abstract class AbstractEntity<ID> implements Persistable<ID> {

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew(){
        this.isNew = false;
    }
}
