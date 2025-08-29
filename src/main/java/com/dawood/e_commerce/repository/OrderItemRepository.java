package com.dawood.e_commerce.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawood.e_commerce.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

}
