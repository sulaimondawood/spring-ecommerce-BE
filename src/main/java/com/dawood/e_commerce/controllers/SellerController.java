package com.dawood.e_commerce.controllers;

import com.dawood.e_commerce.dtos.response.SellerResponseDTO;
import com.dawood.e_commerce.enums.AccountStatus;
import com.dawood.e_commerce.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;


    @GetMapping
    public ResponseEntity<List<SellerResponseDTO>> getSellers(){
        return ResponseEntity.ok().body(sellerService.getAllSellers());
    }

    @GetMapping("/account")
    public ResponseEntity<List<SellerResponseDTO>> getAllSellersByStatus(@RequestParam(name = "status", required = false) AccountStatus status){
        return ResponseEntity.ok(sellerService.getAllSellersByAccountStatus(status));
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<SellerResponseDTO> getSellerById(@PathVariable UUID sellerId ){
        return new ResponseEntity<>(sellerService.getSellerById(sellerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SellerResponseDTO> setupSellerAccount(){
        return null;
    }

}
