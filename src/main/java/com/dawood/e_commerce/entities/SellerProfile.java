package com.dawood.e_commerce.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SellerProfile {

    @Id
    @GeneratedValue
    private UUID id;

    @Embedded
    BusinessDetails businessDetails = new BusinessDetails();

    @Embedded
    BankDetails BankDetails = new BankDetails();

    @OneToOne
    private User user;

}
