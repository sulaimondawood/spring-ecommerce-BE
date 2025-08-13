package com.dawood.e_commerce.exceptions;

public class ProductException extends RuntimeException {

    public ProductException(){
        super("You're not authorised to perform this action!");
    }

    public ProductException(String message) {
        super(message);
    }
}
