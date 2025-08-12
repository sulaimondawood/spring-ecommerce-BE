package com.dawood.e_commerce.repository;

import com.dawood.e_commerce.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategory, UUID> {
}
