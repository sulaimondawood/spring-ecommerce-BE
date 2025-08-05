package com.dawood.e_commerce.entities;

import com.dawood.e_commerce.enums.AccountStatus;
import com.dawood.e_commerce.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Seller {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String passowrd;

    @OneToOne
    private Address address = new Address();

    @Embedded
    private BankDetails bankDetails = new BankDetails();

    @Embedded
    private BusinessDetails businessDetails = new BusinessDetails();

    @Enumerated(EnumType.STRING )
    private UserRole role = UserRole.PARTNER;

    private boolean isEmailVerified = false;

    private AccountStatus accountStatus = AccountStatus.NOT_VERIFIED;

    @OneToMany(mappedBy = "seller")
    private List<Product> product;


}
