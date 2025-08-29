package com.dawood.e_commerce.exceptions.order;

import com.dawood.e_commerce.exceptions.EcommerceException;

public class OrderException extends EcommerceException {

  public OrderException(String message) {
    super(message);
  }

  public OrderException() {
    super("Invalid order");
  }

}
