package com.dawood.e_commerce.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dawood.e_commerce.entities.SellerOrder;

@Repository
public interface SellerOrderRepository extends JpaRepository<SellerOrder, UUID> {

}
