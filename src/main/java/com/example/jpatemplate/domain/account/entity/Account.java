package com.example.jpatemplate.domain.account.entity;


import com.example.jpatemplate.domain.account.exception.NotEnoughBalanceException;
import com.example.jpatemplate.domain.customer.entity.Customer;
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

    public void decreaseBalance(double amount){
        if(balance-amount<0){
            throw new NotEnoughBalanceException("not enough balance");
        }
        balance -= amount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", customer=" + (customer!=null?customer.getId():customer) +
                '}';
    }
}
