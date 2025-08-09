package com.dawood.e_commerce.exceptions;

public class UsernamePasswordException extends EcommerceException{

    public UsernamePasswordException(){
        super("Email or password is incorrect");
    }

    public UsernamePasswordException(String message){
        super(message);
    }
}
