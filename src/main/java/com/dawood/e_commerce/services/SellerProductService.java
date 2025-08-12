package com.dawood.e_commerce.services;

import com.dawood.e_commerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerProductService {

    private final ProductRepository productRepository;



}
