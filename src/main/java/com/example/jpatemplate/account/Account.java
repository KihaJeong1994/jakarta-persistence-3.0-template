package com.example.jpatemplate.account;


import com.example.jpatemplate.user.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    private Long id;

    private Double balance;

    @ManyToOne
    private Customer customer;

    @Builder
    public Account(Long id, Double balance, Customer customer) {
        this.id = id;
        this.balance = balance;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", customer=" + customer.getId() +
                '}';
    }
}
