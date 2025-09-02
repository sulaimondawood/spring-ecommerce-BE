package com.dawood.e_commerce.entities;

import com.dawood.e_commerce.enums.AccountStatus;
import com.dawood.e_commerce.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @PrePersist
    public void prePersit() {
        this.isEmailVerified = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String fullname;

    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Boolean isEmailVerified;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.NOT_VERIFIED;

    @OneToMany
    private List<Address> addresses = new ArrayList<>();

    @ManyToMany
    private Set<Coupon> usedCoupons = new HashSet<>();

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL)
    private SellerProfile sellerProfile;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> products;

    @OneToOne
    @JsonIgnore
    private Cart cart;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<MasterOrder> masterOrder;

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private SellerReport sellerReport;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Wishlist wishlist;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
