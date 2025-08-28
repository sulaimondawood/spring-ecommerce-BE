package com.dawood.e_commerce.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawood.e_commerce.dtos.request.CartRequestDTO;
import com.dawood.e_commerce.entities.Cart;
import com.dawood.e_commerce.services.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @GetMapping
  public ResponseEntity<Cart> getUserCart() {
    return ResponseEntity.ok().body(cartService.getUserCart());
  }

  @PostMapping
  public ResponseEntity<Cart> addToCart(@RequestBody CartRequestDTO requestDTO) {
    return new ResponseEntity<>(cartService.addToCart(requestDTO), HttpStatus.CREATED);

  }

  @PatchMapping
  public ResponseEntity<Cart> updateCart(@RequestBody CartRequestDTO requestDTO) {
    return new ResponseEntity<>(cartService.updateCart(requestDTO), HttpStatus.CREATED);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCart(@PathVariable UUID id) {

    cartService.deleteCartItem(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
