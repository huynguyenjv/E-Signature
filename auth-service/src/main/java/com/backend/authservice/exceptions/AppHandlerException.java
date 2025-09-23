package com.backend.authservice.exceptions;

public class AppHandlerException extends RuntimeException {
    public AppHandlerException(String message) {
        super(message);
    }
}
