package com.dawood.e_commerce.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BusinessDetailsDTO {

    @NotBlank(message = "Business name is required")
    private String businessName;

    @NotBlank(message = "Business email is required")
    @Email(message = "Email format is invalid")
    private String businessEmail;

    @NotBlank(message = "Business address is required")
    private String businessAddress;

    @NotBlank(message = "Business number is required")
    private String businessNumber;

    @NotBlank(message = "Business image is required")
    private String businessImage;

    private String businessBanner;

}
