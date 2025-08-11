package com.dawood.e_commerce.dtos.response;

import com.dawood.e_commerce.enums.AccountStatus;
import com.dawood.e_commerce.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerResponseDTO {

    private UUID uuid;

    private String fullname;

    private String phoneNumber;

    private String email;

    private UserRole role;

    private Boolean isEmailVerified;

    private AccountStatus accountStatus;
}
