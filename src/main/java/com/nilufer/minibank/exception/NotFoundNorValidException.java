package com.nilufer.minibank.exception;

public class NotFoundNorValidException extends RuntimeException {
    public NotFoundNorValidException(String message) {
        super(message);
    }
}
