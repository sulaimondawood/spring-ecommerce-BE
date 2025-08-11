package com.dawood.e_commerce.exceptions;

import com.dawood.e_commerce.dtos.response.ErrorDetails;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> usernamePasswordHandler(BadCredentialsException ex){

        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code("BAD_CREDENTIALS")
                .build();

        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> userNotFoundHandler(UserNotFoundException ex){

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setCode("NOT_FOUND");
        errorDetails.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> userAlreadyExistsHandler(UserAlreadyExistsException ex){
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage() != null? ex.getMessage() : "User already exists");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationError(MethodArgumentNotValidException ex){

        log.error("Validation Exception: {}",ex.getMessage());
        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach((error)->{
                    validationErrors.put(error.getField(),error.getDefaultMessage());
                });

        ErrorDetails errorDetails = ErrorDetails.builder()
                .code("VALIDATION_ERROR")
                .message("Input validation failed")
                .validationErrors(validationErrors)
                .build();

        return ResponseEntity.badRequest().body(errorDetails);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex){

        log.error("Unexpected error occurred {}",ex.getMessage());

        ErrorDetails error = ErrorDetails.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("An unexpected error occurred")
                .build();

        return ResponseEntity.badRequest().body(error);
    }

}
