package com.example.jpatemplate.domain.account.entity;

import com.example.jpatemplate.domain.account.exception.NotEnoughBalanceException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void decreaseBalance_concurrency_test() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Account account = Account.builder().id(1L).balance(1.0).build();
        Future<?> submit1 = executorService.submit(() -> {
            account.decreaseBalance(1.0);
            System.out.println("decrease success1");
        });
        Future<?> submit2 = executorService.submit(() -> {
            account.decreaseBalance(1.0);
            System.out.println("decrease success2");
        });

        assertThrows(NotEnoughBalanceException.class,()->{
           try {
               submit1.get();
               submit2.get();
           }catch (ExecutionException ex){
               System.out.println(ex.getMessage());
               throw ex.getCause();
           }
        });
    }
}