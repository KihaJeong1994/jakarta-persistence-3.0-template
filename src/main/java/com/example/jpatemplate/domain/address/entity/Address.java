package com.example.jpatemplate.domain.address.entity;

import com.example.jpatemplate.common.jpa.entity.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Address extends AbstractEntity {

    @Id
    private Long id;

    private String country;
    private String details;

    @Builder
    public Address(Long id, String country, String details) {
        this.id = id;
        this.country = country;
        this.details = details;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
