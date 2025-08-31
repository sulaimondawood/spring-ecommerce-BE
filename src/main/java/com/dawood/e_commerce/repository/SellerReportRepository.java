package com.dawood.e_commerce.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawood.e_commerce.entities.SellerReport;

public interface SellerReportRepository extends JpaRepository<SellerReport, UUID> {
  SellerReport findBySellerUuid(UUID sellerId);
}
