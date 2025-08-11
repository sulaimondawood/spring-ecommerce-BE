package com.dawood.e_commerce.entities;


import jakarta.persistence.Embeddable;

@Embeddable
public class BusinessDetails {

    private String businessName;

    private String businessEmail;

    private String businessAddress;

    private String businessNumber;

    private String businessImage;

    private String businessBanner;
}
