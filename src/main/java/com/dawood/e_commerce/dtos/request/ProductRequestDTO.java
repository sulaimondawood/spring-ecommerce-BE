package com.dawood.e_commerce.dtos.request;

import java.util.List;
import java.util.UUID;

import com.dawood.e_commerce.enums.ProductStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is requierd")
    private String description;

    @Positive(message = "Product price must be greater than 0")
    private long price;

    @Positive(message = "Maximum retail price must be greater than 0")
    private long mrpPrice;

    private int discount;

    @Positive(message = "Stock quantity can not be less than 0")
    private int stockQuantity;

    private ProductStatus status;

    @NotEmpty(message = "At least one category ID is required")
    private List<UUID> category;

    private String size;

    private String color;

    @NotEmpty(message = "Product image is required")
    private List<String> images;
}
