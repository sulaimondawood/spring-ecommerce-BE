package com.dawood.e_commerce.repository;

import com.dawood.e_commerce.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
}
