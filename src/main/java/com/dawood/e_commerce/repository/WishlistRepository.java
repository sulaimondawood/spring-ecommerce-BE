package com.dawood.e_commerce.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dawood.e_commerce.entities.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, UUID> {

  Wishlist findByCustomerUuid(UUID id);

}
