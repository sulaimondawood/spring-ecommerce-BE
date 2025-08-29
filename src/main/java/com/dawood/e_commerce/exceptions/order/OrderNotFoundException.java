package com.dawood.e_commerce.exceptions.order;

import com.dawood.e_commerce.exceptions.EcommerceException;

public class OrderNotFoundException extends EcommerceException {

  public OrderNotFoundException(String message) {
    super(message);
  }

}
