package com.dawood.e_commerce.mapper;

import com.dawood.e_commerce.dtos.response.ProductResponseDTO;
import com.dawood.e_commerce.entities.Product;

public class ProductMapper {

    public static ProductResponseDTO toDTO(Product product){
        return ProductResponseDTO.builder()
                .id(product.getId())
                .color(product.getColor())
                .price(product.getPrice())
                .name(product.getName())
                .size(product.getSize())
                .description(product.getDescription())
                .stockQuantity(product.getStockQuantity())
                .status(product.getStatus())
                .discount(product.getDiscount())
                .images(product.getImages())
                .mrpPrice(product.getMrpPrice())
                .category(product.getCategory())
                .status(product.getStatus())
                .build();
    }

    public static Product toModel(ProductResponseDTO productResponseDTO){
        Product product = new Product();

        product.setId(productResponseDTO.getId());
        product.setName(productResponseDTO.getName());
        product.setDescription(product.getDescription());
        product.setPrice(productResponseDTO.getPrice());
        product.setMrpPrice(productResponseDTO.getMrpPrice());
        product.setDiscount(productResponseDTO.getDiscount());
        product.setStockQuantity(productResponseDTO.getStockQuantity());
        product.setStatus(productResponseDTO.getStatus());
        product.setCategory(productResponseDTO.getCategory());
        product.setSize(productResponseDTO.getSize());
        product.setColor(productResponseDTO.getColor());
        product.setImages(productResponseDTO.getImages());

        return product;
    }
}
