package com.dawood.e_commerce.dtos.request;

import java.util.UUID;

import lombok.Data;

@Data
public class CartRequestDTO {
  private int quantity;

  private UUID productId;
}
