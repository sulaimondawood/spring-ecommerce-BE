package com.dawood.e_commerce.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dawood.e_commerce.dtos.request.CancelOrderRequest;
import com.dawood.e_commerce.dtos.request.CheckoutRequestDTO;
import com.dawood.e_commerce.dtos.response.order.MasterOrderPagedResponse;
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

  @GetMapping
  public ResponseEntity<MasterOrderPagedResponse> getAllMasterOrders(
      @RequestParam(defaultValue = "0", required = false) int pageNo,
      @RequestParam(defaultValue = "20", required = false) int pageSize,
      @RequestParam(required = false) String query) {
    return new ResponseEntity<>(orderService.getAllMasterOrders(pageNo, pageSize, query), HttpStatus.OK);
  }

  @GetMapping("/customer/history")
  public ResponseEntity<MasterOrderPagedResponse> getCustomerHistory(
      @RequestParam(defaultValue = "0", required = false) int pageNo,
      @RequestParam(defaultValue = "20", required = false) int pageSize,
      @RequestParam(required = false) String query) {

    return new ResponseEntity<>(orderService.customerHistory(pageNo, pageSize, query), HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity<Map<String, String>> cancelOrder(@RequestBody @Valid CancelOrderRequest order) {

    Map<String, String> response = new HashMap<>();

    orderService.cancelOrder(order.getOrderId());

    response.put("message", "Order succesfully cancelled");

    return ResponseEntity.ok(response);
  }

  @GetMapping("{orderId}")
  public ResponseEntity<MasterOrder> getOrder(@PathVariable UUID orderId) {

    MasterOrder response = orderService.getOrderById(orderId);

    return ResponseEntity.ok(response);
  }

}
