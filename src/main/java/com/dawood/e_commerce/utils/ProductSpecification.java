package com.dawood.e_commerce.utils;

import org.springframework.data.jpa.domain.Specification;

import com.dawood.e_commerce.entities.Product;
import com.dawood.e_commerce.entities.ProductCategory;
import com.dawood.e_commerce.enums.ProductStatus;

import jakarta.persistence.criteria.Join;

public class ProductSpecification {

  public static Specification<Product> containsName(String name) {
    return (root, query, criteriaBuilder) -> {
      if (name == null)
        return criteriaBuilder.conjunction();
      return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    };
  }

  public static Specification<Product> hasCategory(ProductCategory category) {
    return (root, query, criteriaBuilder) -> {
      if (category == null) {
        return criteriaBuilder.conjunction(); // no filter applied if null
      }
      Join<Object, Object> categories = root.join("category");
      return criteriaBuilder.equal(categories.get("id"), category.getId());
    };
  }

  public static Specification<Product> hasSize(String size) {
    return (root, query, criterialBuilder) -> {
      if (size == null)
        return criterialBuilder.conjunction();
      return criterialBuilder.equal(root.get("size"), size);
    };
  }

  public static Specification<Product> hasStatus(ProductStatus status) {
    return (root, query, criteriaBuilder) -> {
      if (status == null)
        return criteriaBuilder.conjunction();

      return criteriaBuilder.equal(root.get("status"), status);
    };

  }

  public static Specification<Product> priceBetween(Long minPrice, Long maxPrice) {
    return (root, query, criteriaBuilder) -> {
      if (minPrice != null && maxPrice != null) {
        return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
      } else if (minPrice != null && maxPrice == null) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
      } else if (minPrice == null && maxPrice != null) {
        return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
      }

      return criteriaBuilder.conjunction();

    };
  }

  public static Specification<Product> containsDescription(String description) {
    return (root, query, criteriaBuilder) -> {
      if (description == null)
        return criteriaBuilder.conjunction();

      return criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description + "%");
    };
  }

}
