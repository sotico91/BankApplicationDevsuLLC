package com.devsu.hackerearth.backend.account.exceptions;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(String mensaje) {
        super(mensaje);
    }
}
