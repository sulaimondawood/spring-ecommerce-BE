package com.dawood.e_commerce.exceptions;

public class EcommerceException extends RuntimeException{
    public EcommerceException() {
    }

    public EcommerceException(String message) {
        super(message);
    }

    public EcommerceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EcommerceException(Throwable cause) {
        super(cause);
    }

    public EcommerceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
