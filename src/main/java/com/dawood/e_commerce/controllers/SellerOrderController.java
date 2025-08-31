package com.dawood.e_commerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawood.e_commerce.dtos.response.order.SellerOrderPagedResponse;

@RestController
@RequestMapping("/seller/orders")
public class SellerOrderController {

  public ResponseEntity<SellerOrderPagedResponse> getSellerHistory() {
    return null;
  }

}
