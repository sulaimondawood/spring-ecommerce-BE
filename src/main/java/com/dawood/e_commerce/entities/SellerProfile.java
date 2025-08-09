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

    private String businessName;

    private String businessEmail;

    private String businessAddress;

    private String businessNumber;

    private String businessImage;

    private String businessBanner;

    @OneToOne
    private User user;

}
