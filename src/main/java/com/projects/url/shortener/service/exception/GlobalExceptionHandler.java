package com.projects.url.shortener.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGenericException(Exception exception){

        Map<String,Object> response = new HashMap<>();

        response.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error","Internal Server Error");
        response.put("message",exception.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);

    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidUrlException(InvalidUrlException exception){

        Map<String,Object> response = new HashMap<>();

        response.put("status",HttpStatus.BAD_REQUEST.value());
        response.put("error","Bad Request");
        response.put("message",exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid request");

        Map<String, Object> response = new HashMap<>();

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(IdempotencyConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflictingUrls(
            IdempotencyConflictException exception) {

        Map<String, Object> response = new HashMap<>();

        response.put("status",HttpStatus.CONFLICT.value());
        response.put("error","Conflict");
        response.put("message",exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Map<String, Object>> handleMissingHeaders(MissingRequestHeaderException exception){

        Map<String, Object> response = new HashMap<>();

        response.put("status",HttpStatus.BAD_REQUEST.value());
        response.put("error","Bad Request");
        response.put("message",exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidArgument(IllegalArgumentException exception){

        Map<String, Object> response = new HashMap<>();

        response.put("status",HttpStatus.BAD_REQUEST.value());
        response.put("error","Bad Request");
        response.put("message",exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);


    }







}
