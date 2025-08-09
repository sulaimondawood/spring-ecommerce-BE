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
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String accountNumber;

    private String accountHolderName;

    private String bankName;

    @OneToOne
    private User user;

}
