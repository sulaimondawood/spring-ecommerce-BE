package com.dawood.e_commerce.exceptions.order;

import com.dawood.e_commerce.exceptions.EcommerceException;

public class InvalidOrderTransitionException extends EcommerceException {

  public InvalidOrderTransitionException(String message) {
    super(message);
  }

  public InvalidOrderTransitionException() {
    super("Order transition is invalid");
  }

}
