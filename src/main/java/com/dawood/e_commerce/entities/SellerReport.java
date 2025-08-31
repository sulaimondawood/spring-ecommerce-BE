package com.dawood.e_commerce.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerReport {

  @Id
  @GeneratedValue
  private UUID id;

  private int totalOrders;

  private long totalEarnings;

  private long totalTransactions;

  private long totalSales;

  private long cancelledOrders;

}
