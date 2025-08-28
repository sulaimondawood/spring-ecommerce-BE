package com.dawood.e_commerce.dtos.request;

import com.dawood.e_commerce.entities.Address;

import lombok.Data;

@Data
public class CheckoutRequestDTO {

  private Address address;

}
