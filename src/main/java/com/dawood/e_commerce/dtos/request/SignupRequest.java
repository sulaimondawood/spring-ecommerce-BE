package com.dawood.e_commerce.dtos.request;

import com.dawood.e_commerce.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank(message = "Full name is required")
    private String fullname;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private UserRole role;

}
