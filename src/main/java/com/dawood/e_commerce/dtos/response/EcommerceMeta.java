package com.dawood.e_commerce.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EcommerceMeta {

  private int pageNumber;

  private int pageSize;

  private int totalPages;

  private boolean hasNext;

  private boolean hasPrev;
}
