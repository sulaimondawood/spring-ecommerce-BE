package com.dawood.e_commerce.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dawood.e_commerce.dtos.response.order.SellerOrderPagedResponse;
import com.dawood.e_commerce.entities.SellerOrder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/seller/orders")
@RequiredArgsConstructor
public class SellerOrderController {

  private final SellerOrderService sellerOrderService;

  @GetMapping
  public ResponseEntity<SellerOrderPagedResponse> getAllSellersOrders(
      @RequestParam(defaultValue = "0", required = false) int pageNo,
      @RequestParam(defaultValue = "20", required = false) int pageSize,
      @RequestParam(required = false) String query) {
    return new ResponseEntity<>(sellerOrderService.getAllSellerOders(pageNo, pageSize, query), HttpStatus.OK);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<SellerOrder> getSellerOrderById(@PathVariable UUID orderId) {
    return ResponseEntity.ok(sellerOrderService.getSellerOrderById(orderId));
  }

}
