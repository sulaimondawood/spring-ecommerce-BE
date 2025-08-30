package com.dawood.e_commerce.utils;

import org.springframework.data.jpa.domain.Specification;

import com.dawood.e_commerce.entities.MasterOrder;

public class OrderSpecification {

  public static Specification<MasterOrder> hasOrderId(String orderId) {
    return (root, query, criteriaBuilder) -> {
      if (orderId == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("orderId"), orderId);
    };
  }

}
