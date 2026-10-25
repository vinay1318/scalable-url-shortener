package com.projects.url.shortener.service.exception;

public class IdempotencyConflictException extends RuntimeException {

    public IdempotencyConflictException(String message){
        super(message);
    }

}
