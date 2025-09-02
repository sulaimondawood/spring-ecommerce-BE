package com.dawood.e_commerce.dtos.request;

import java.util.UUID;

import lombok.Data;

@Data
public class WishlistRequestDTO {
  private UUID productId;
}
