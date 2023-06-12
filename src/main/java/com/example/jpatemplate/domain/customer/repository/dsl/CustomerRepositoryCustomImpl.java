package com.example.jpatemplate.domain.customer.repository.dsl;

import com.example.jpatemplate.domain.customer.entity.Customer;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

import static com.example.jpatemplate.domain.customer.entity.QCustomer.customer;

@Repository
public class CustomerRepositoryCustomImpl extends QuerydslRepositorySupport implements CustomerRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    public CustomerRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        super(Customer.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Customer> findByAddressCountryAndAddressDetails(String country, String details, Pageable pageable) {
        List<Customer> customers = jpaQueryFactory.selectFrom(customer)
                .where(
                        countryContainsIgnoreCase(country)
                        , detailsContainsIgnoreCase(details)
                )
                .orderBy(pageable.getSort().stream().map(
                        this::getOrderSpecifier
                ).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return PageableExecutionUtils.getPage(customers,pageable,from(customer)::fetchCount);
    }



    private OrderSpecifier<? extends Serializable> getOrderSpecifier(Sort.Order order){
        String orderProperty = order.getProperty();
        if("customerId".equals(orderProperty)){
            if(order.getDirection().equals(Sort.Direction.ASC)){
                return customer.customerId.asc();
            }else{
                return customer.customerId.desc();
            }
        }else {
            throw new IllegalArgumentException("Unsupported sort property: " + orderProperty);
        }
    }

    private static BooleanExpression detailsContainsIgnoreCase(String details) {
        if(StringUtils.hasText(details)){
            return customer.address.details.containsIgnoreCase(details);
        }
        return null;
    }

    private static BooleanExpression countryContainsIgnoreCase(String country) {
        if(StringUtils.hasText(country)){
            return customer.address.country.containsIgnoreCase(country);
        }
        return null;
    }
}
