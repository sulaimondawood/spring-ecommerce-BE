package com.dawood.e_commerce.dtos.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPaginationResponse {

  private List<ProductResponseDTO> content;

  private EcommerceMeta meta;

}
