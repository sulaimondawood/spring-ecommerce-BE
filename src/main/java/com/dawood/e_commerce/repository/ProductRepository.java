package com.dawood.e_commerce.repository;

import com.dawood.e_commerce.entities.Product;
import com.dawood.e_commerce.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

  Page<Product> findAllBySellerOrderByCreatedAtDesc(User seller, Pageable pageable);

}
