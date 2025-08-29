package com.dawood.e_commerce.exceptions.user;

import com.dawood.e_commerce.exceptions.EcommerceException;
 
public class UserAlreadyExistsException extends EcommerceException {
    public UserAlreadyExistsException(){

    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
