package com.dawood.e_commerce.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Coupon {

    @Id
    @GeneratedValue
    private UUID id;

    private String code;

    private long discountPercentage;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean isActive = false;

    private double minimumOrderValue;

    @ManyToMany(mappedBy = "usedCoupons")
    private Set<User> users = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
