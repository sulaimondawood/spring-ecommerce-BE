package com.dawood.e_commerce.services;

import com.dawood.e_commerce.dtos.request.ProductRequestDTO;
import com.dawood.e_commerce.dtos.response.ProductResponseDTO;
import com.dawood.e_commerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellerProductService {

    private final ProductRepository productRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO){

        return null;
    }

    public void deleteProduct(UUID productId){

    }

    public ProductResponseDTO updateProduct(ProductRequestDTO requestDTO){
        return null;
    }

    public Page<ProductResponseDTO> getAllProducts(){
        return  null;
    }



}
