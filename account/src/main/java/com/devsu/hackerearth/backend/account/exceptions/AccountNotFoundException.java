package com.devsu.hackerearth.backend.account.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String mensaje){
        super (mensaje);
    }
}