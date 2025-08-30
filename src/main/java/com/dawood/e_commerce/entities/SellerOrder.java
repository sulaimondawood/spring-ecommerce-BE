package com.dawood.e_commerce.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dawood.e_commerce.enums.OrderStatus;
import com.dawood.e_commerce.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class SellerOrder {

  @GeneratedValue
  @Id
  private UUID id;

  private String sellerOrderId;

  @ManyToOne
  private User customer;

  @ManyToOne
  @JsonBackReference
  private MasterOrder order;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "sellerOrder")
  @JsonManagedReference
  private List<OrderItem> orderItems = new ArrayList<>();

  @ManyToOne
  private Address shippingAddress;

  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @Enumerated(EnumType.STRING)
  private OrderStatus sellOrderStatus;

  // tracking information
  @Column(unique = true)
  private String trackingCode;

  private LocalDateTime estimatedDeliveryDate;

  private LocalDateTime shippedAt;

  private LocalDateTime deliveredAt;

  private long subtotal;

  private long shippingCost;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

}
