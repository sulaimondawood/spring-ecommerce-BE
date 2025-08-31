package com.dawood.e_commerce.dtos.request;

import com.dawood.e_commerce.enums.OrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderRequest {

  @NotNull
  private OrderStatus status;
}
