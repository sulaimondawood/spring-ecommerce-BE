package com.dawood.e_commerce.utils;

import java.util.UUID;

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

  public static Specification<MasterOrder> hasCustomerId(UUID id) {
    return (root, query, cb) -> {
      if (id == null)
        return cb.conjunction();

      return cb.equal(root.get("customer").get("uuid"), id);
    };
  }

}
