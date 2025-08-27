package com.dawood.e_commerce.controllers;

import com.dawood.e_commerce.dtos.request.ProductRequestDTO;
import com.dawood.e_commerce.dtos.request.ProductUpdateRequestDTO;
import com.dawood.e_commerce.dtos.request.SellerProfileDTO;
import com.dawood.e_commerce.dtos.response.ProductPaginationResponse;
import com.dawood.e_commerce.dtos.response.ProductResponseDTO;
import com.dawood.e_commerce.dtos.response.SellerResponseDTO;
import com.dawood.e_commerce.entities.BankDetails;
import com.dawood.e_commerce.entities.BusinessDetails;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.enums.AccountStatus;
import com.dawood.e_commerce.services.SellerProductService;
import com.dawood.e_commerce.services.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;
    private final SellerProductService sellerProductService;

    @GetMapping
    public ResponseEntity<List<SellerResponseDTO>> getSellers() {
        return ResponseEntity.ok().body(sellerService.getAllSellers());
    }

    @GetMapping("/account")
    public ResponseEntity<List<SellerResponseDTO>> getAllSellersByStatus(
            @RequestParam(name = "status", required = false) AccountStatus status) {
        return ResponseEntity.ok(sellerService.getAllSellersByAccountStatus(status));
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<SellerResponseDTO> getSellerById(@PathVariable UUID sellerId) {
        return new ResponseEntity<>(sellerService.getSellerById(sellerId), HttpStatus.OK);
    }

    @PostMapping("/account/setup")
    public ResponseEntity<Map<String, String>> setupSellerAccount(@Valid @RequestBody SellerProfileDTO request,
            @RequestHeader("Authorization") String jwt) {

        Map<String, String> response = new HashMap<>();
        response.put("message", "Account setup is successful");

        sellerService.setupSellerProfile(jwt, request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/account/profile")
    public ResponseEntity<User> getSellerProfile(@RequestHeader("Authorization") String jwt) {
        return new ResponseEntity<>(sellerService.getSellerProfile(jwt), HttpStatus.OK);
    }

    @PatchMapping("/account/bank")
    public ResponseEntity<BankDetails> updateBankDetails(@Valid @RequestBody BankDetails request) {
        return ResponseEntity.ok().body(sellerService.updateBankDetails(request));
    }

    @PatchMapping("/account/business-details")
    public ResponseEntity<BusinessDetails> updateBusinessDetails(@RequestBody BusinessDetails request) {
        return new ResponseEntity<>(sellerService.updateBusinessInfo(request), HttpStatus.OK);
    }

    @PostMapping("/create-product")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return new ResponseEntity<>(sellerProductService.createProduct(productRequestDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/update-product/{product-id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestBody ProductUpdateRequestDTO productRequestDTO,
            @PathVariable(name = "product-id") UUID productId) {
        return new ResponseEntity<>(sellerProductService.updateProduct(productRequestDTO, productId), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<ProductPaginationResponse> getAllProducts(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize) {
        return new ResponseEntity(sellerProductService.getAllProducts(pageSize, pageNumber), HttpStatus.OK);
    }
}
