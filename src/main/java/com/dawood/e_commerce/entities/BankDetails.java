package com.dawood.e_commerce.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
@EntityListeners(AuditingEntityListener.class)
public class BankDetails {

    private String accountNumber;

    private String accountHolderName;

    private String bankName;

}
