package com.dawood.e_commerce.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Wishlist {

  @Id
  @GeneratedValue
  private UUID id;

  @OneToOne
  private User customer;

  @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Product> products;

}
