package com.dawood.e_commerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawood.e_commerce.dtos.request.CheckoutRequestDTO;
import com.dawood.e_commerce.entities.MasterOrder;
import com.dawood.e_commerce.services.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/create")
  public ResponseEntity<MasterOrder> placeOrder(@RequestBody @Valid CheckoutRequestDTO requestDTO) {
    return new ResponseEntity<>(orderService.createOrder(requestDTO), HttpStatus.CREATED);
  }

}
