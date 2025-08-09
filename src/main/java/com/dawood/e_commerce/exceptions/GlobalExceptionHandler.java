package com.dawood.e_commerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,String>> userNotFoundHandler(UserNotFoundException ex){
        Map<String, String> response = new HashMap<>();
        response.put("message","User not found");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> userAlreadyExistsHandler(UserAlreadyExistsException ex){
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage() != null? ex.getMessage() : "User already exists");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
