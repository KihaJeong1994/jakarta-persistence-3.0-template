package com.example.jpatemplate.domain.account.exception;

public class NotEnoughBalanceException extends RuntimeException{
    public NotEnoughBalanceException(String message) {
        super(message);
    }
}
