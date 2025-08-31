package com.dawood.e_commerce.dtos.response.order;

import java.util.List;

import com.dawood.e_commerce.dtos.response.EcommerceMeta;
import com.dawood.e_commerce.entities.SellerOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerOrderPagedResponse {

  private List<SellerOrder> orders;

  private EcommerceMeta meta;

}
