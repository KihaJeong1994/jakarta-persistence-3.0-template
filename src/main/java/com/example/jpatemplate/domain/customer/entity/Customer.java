package com.example.jpatemplate.domain.customer.entity;

import com.example.jpatemplate.common.jpa.entity.AbstractEntity;
import com.example.jpatemplate.domain.account.entity.Account;
import com.example.jpatemplate.domain.address.entity.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Customer extends AbstractEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email // JPA validation works when flushed
    private String customerId;

    @OneToOne(cascade = CascadeType.PERSIST,orphanRemoval = true) // insert&remove address when insert&remove customer
    private Address address;

    @OneToMany(mappedBy = "customer" // field name of Customer in owning side
            ,cascade = CascadeType.PERSIST // hibernate need CascadeType.PERSIST option for orphanRemoval since persist on Customer will propagate the persist operation to the Account
            , orphanRemoval = true)
    private List<Account> accounts ;

    @Builder
    public Customer(Long id, String customerId, Address address, List<Account> accounts) {
        this.id = id;
        this.customerId = customerId;
        this.address = address;
        this.accounts = accounts!=null ? accounts: new ArrayList<>();
    }

    // method to manage bidirectional relationship between entity
    public void addAccount(Account account){
        this.accounts.add(account);
        account.setCustomer(this);
    }

    // method to manage bidirectional relationship between entity
    public void removeAccount(Account account){
        this.accounts.remove(account);
        account.setCustomer(null);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                ", address=" + address +
                ", accounts=" + accounts +
                '}';
    }
}
