package com.quantity.measurement.exception;

public class Exception extends RuntimeException {

    public Exception(String message) {
        super(message);
    }

    public Exception(String message, Throwable cause) {
        super(message, cause);
    }
}