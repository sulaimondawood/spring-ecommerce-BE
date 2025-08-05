package com.dawood.e_commerce.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
@EntityListeners(AuditingEntityListener.class)
public class BusinessDetails {

    private String businessName;

    private String businessEmail;

    private String businessAddress;

    private String businessNumber;

    private String businessImage;

    private String businessBanner;

}
