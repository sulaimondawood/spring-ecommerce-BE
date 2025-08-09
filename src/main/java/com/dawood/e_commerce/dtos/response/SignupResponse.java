package com.dawood.e_commerce.dtos.response;


import com.dawood.e_commerce.enums.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignupResponse {

    private String fullname;

    private String phoneNumber;

    private String email;


    private UserRole role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
