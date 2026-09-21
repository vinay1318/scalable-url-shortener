package com.projects.url.shortener.service.exception;


public class InvalidUrlException extends RuntimeException{


    public InvalidUrlException(String message) {

        super(message);

    }
}
