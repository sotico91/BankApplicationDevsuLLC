package com.devsu.hackerearth.backend.account.exceptions;

public class TransactionNotFoundException extends RuntimeException{

    public TransactionNotFoundException(String mensaje){
        super(mensaje);
    }
    
}
