package com.dawood.e_commerce.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dawood.e_commerce.enums.PaymentGateway;
import com.dawood.e_commerce.enums.PaymentMethod;
import com.dawood.e_commerce.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Payment {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(unique = true, nullable = false)
  private String paymentReference;

  private String orderId;

  private long amount;

  private String customerId;

  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;

  @Enumerated(EnumType.STRING)
  private PaymentGateway paymentGateway;

  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  private String transactionReference;

  private LocalDateTime initiatedAt;

  private LocalDateTime completedAt;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

}
