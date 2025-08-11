package com.dawood.e_commerce.controllers;

import com.dawood.e_commerce.dtos.response.SellerResponseDTO;
import com.dawood.e_commerce.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<SellerResponseDTO>> getSellers(){
        return ResponseEntity.ok().body(sellerService.getAllSellers());
    }

    public ResponseEntity<SellerResponseDTO> setupSellerAccount(){
        return null;
    }

}
