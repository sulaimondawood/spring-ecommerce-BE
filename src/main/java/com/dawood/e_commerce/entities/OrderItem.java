package com.dawood.e_commerce.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {

  @GeneratedValue
  @Id
  private UUID id;

  private String size;

  private long sellingPrice;

  private long mrpPrice;

  @JsonBackReference
  @ManyToOne
  private MasterOrder masterOrder;

  @JsonBackReference
  @ManyToOne(cascade = CascadeType.ALL)
  private SellerOrder sellerOrder;

  @ManyToOne
  private Product product;

  private int quantity;

}
