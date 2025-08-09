package com.dawood.e_commerce.exceptions;

public class UserAlreadyExistsException extends EcommerceException {
    public UserAlreadyExistsException(){

    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
