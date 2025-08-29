package com.dawood.e_commerce.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawood.e_commerce.entities.Address;

public interface AddressRepository extends JpaRepository<Address, UUID> {

}
