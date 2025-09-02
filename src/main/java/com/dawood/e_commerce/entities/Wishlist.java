package com.dawood.e_commerce.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wishlist {

  @Id
  @GeneratedValue
  private UUID id;

  @OneToOne
  @JsonIgnore
  private User customer;

  @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Product> products;

}
