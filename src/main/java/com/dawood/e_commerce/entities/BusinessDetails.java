package com.dawood.e_commerce.entities;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDetails {

    private String businessName;

    private String businessEmail;

    private String businessAddress;

    private String businessNumber;

    private String businessImage;

    private String businessBanner;
}
