package com.danthy.pizzafun.domain.errors;

public class MethodNotFoundException extends RuntimeException {
    public MethodNotFoundException(String message) {
        super(message);
    }
}
