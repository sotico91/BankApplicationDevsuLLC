package com.devsu.hackerearth.backend.client.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String mensaje) {
        super(mensaje);
    }
}
