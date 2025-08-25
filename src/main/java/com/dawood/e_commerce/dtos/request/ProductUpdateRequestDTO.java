package com.dawood.e_commerce.dtos.request;

import java.util.List;
import java.util.UUID;

import com.dawood.e_commerce.enums.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductUpdateRequestDTO {

    private String name;
   
    private String description;
    
    private long price;

    private long mrpPrice;
   
    private int stockQuantity;

    private ProductStatus status;

    private List<UUID> category;

    private String size;

    private String color;

    private List<String> images;

}
