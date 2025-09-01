package com.dawood.e_commerce.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dawood.e_commerce.dtos.request.UpdateOrderRequest;
import com.dawood.e_commerce.dtos.response.order.SellerOrderPagedResponse;
import com.dawood.e_commerce.entities.SellerOrder;
import com.dawood.e_commerce.entities.SellerReport;
import com.dawood.e_commerce.services.SellerReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerOrderController {

  private final SellerOrderService sellerOrderService;
  private final SellerReportService sellerReportService;

  @GetMapping("/orders")
  public ResponseEntity<SellerOrderPagedResponse> getAllSellersOrders(
      @RequestParam(defaultValue = "0", required = false) int pageNo,
      @RequestParam(defaultValue = "20", required = false) int pageSize,
      @RequestParam(required = false) String query) {
    return new ResponseEntity<>(sellerOrderService.getAllSellerOrders(pageNo, pageSize, query), HttpStatus.OK);
  }

  @GetMapping("/orders/history")
  public ResponseEntity<SellerOrderPagedResponse> getSellerHistory(
      @RequestParam(defaultValue = "0", required = false) int pageNo,
      @RequestParam(defaultValue = "20", required = false) int pageSize,
      @RequestParam(required = false) String query) {
    return new ResponseEntity<>(sellerOrderService.getSellerHistory(pageNo, pageSize, query), HttpStatus.OK);
  }

  @GetMapping("/{orderId}/order")
  public ResponseEntity<SellerOrder> getSellerOrderById(@PathVariable UUID orderId) {
    return ResponseEntity.ok(sellerOrderService.getSellerOrderById(orderId));
  }

  @PatchMapping("/{orderId}/update")
  public ResponseEntity<SellerOrder> updateSellerOrder(@PathVariable UUID orderId,
      @RequestBody @Valid UpdateOrderRequest request) {
    return ResponseEntity.ok(sellerOrderService.updateSellerOrder(orderId, request.getStatus()));
  }

  @GetMapping("/{orderId}/cancel")
  public ResponseEntity<SellerOrder> cancelOrder(@PathVariable UUID orderId) {
    return ResponseEntity.ok(sellerOrderService.cancelSellerOrder(orderId));
  }

  @GetMapping("/orders/report")
  public ResponseEntity<SellerReport> getSellerReport() {
    return ResponseEntity.ok().body(sellerReportService.getSellerReport());
  }

}
