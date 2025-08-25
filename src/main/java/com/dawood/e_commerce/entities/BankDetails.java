package com.dawood.e_commerce.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BankDetails {

    private String accountNumber;

    private String accountHolderName;

    private String bankName;


}
