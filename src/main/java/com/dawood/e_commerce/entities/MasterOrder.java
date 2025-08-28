package com.dawood.e_commerce.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dawood.e_commerce.enums.OrderStatus;
import com.dawood.e_commerce.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MasterOrder {

  @GeneratedValue
  @Id
  private UUID id;

  private String orderId;

  @ManyToOne
  private User customer;

  @OneToMany(mappedBy = "")
  @JsonManagedReference
  private List<SellerOrder> sellerOrders;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<OrderItem> orderItems;

  private long totalAmount;

  private long shippingCost;

  @ManyToOne
  private Address shippingAddress;

  // status
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

}
