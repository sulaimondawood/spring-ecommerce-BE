package com.dawood.e_commerce.dtos.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CancelOrderRequest {

  @NotNull
  private UUID orderId;
}
