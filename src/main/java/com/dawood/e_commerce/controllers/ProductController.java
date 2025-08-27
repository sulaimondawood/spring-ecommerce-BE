package com.dawood.e_commerce.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dawood.e_commerce.dtos.response.ProductPaginationResponse;
import com.dawood.e_commerce.enums.ProductStatus;
import com.dawood.e_commerce.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<ProductPaginationResponse> getAllProducts(

      @RequestParam(required = false, defaultValue = "0") int pageNumber,
      @RequestParam(required = false, defaultValue = "20") int pageSize,
      @RequestParam(required = false) UUID categoryId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) String size,
      @RequestParam(required = false) ProductStatus status,
      @RequestParam(required = false) Long minPrice,
      @RequestParam(required = false) Long maxPrice

  ) {
    return new ResponseEntity<>(productService.getAllProducts(pageNumber, pageSize, categoryId, name, size, description,
        status, minPrice, maxPrice), HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<ProductPaginationResponse> searchProduct(
      @RequestParam(required = false) String query,
      @RequestParam(required = false, defaultValue = "0") int pageNumber,
      @RequestParam(required = false, defaultValue = "20") int pageSize) {
    return ResponseEntity.ok().body(productService.searchProduct(query, pageNumber, pageSize));
  }

}
