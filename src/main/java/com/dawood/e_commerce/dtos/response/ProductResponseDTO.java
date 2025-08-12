package com.dawood.e_commerce.dtos.response;

import com.dawood.e_commerce.entities.ProductCategory;
import com.dawood.e_commerce.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private String name;

    private String description;

    private long price;

    private long mrpPrice;

    private int discount;

    private int stockQuantity;

    private ProductStatus status;

    private List<ProductCategory> category;

    private String size;

    private String color;

    private List<String> images;

}
