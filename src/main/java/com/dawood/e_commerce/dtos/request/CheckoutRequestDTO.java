package com.dawood.e_commerce.dtos.request;

import com.dawood.e_commerce.entities.Address;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class CheckoutRequestDTO {

  @Valid
  private Address address;

}
