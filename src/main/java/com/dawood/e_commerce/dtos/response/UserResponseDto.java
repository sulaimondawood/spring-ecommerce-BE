package com.dawood.e_commerce.dtos.response;

import com.dawood.e_commerce.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private UUID uuid;

    private String fullname;

    private String phoneNumber;

    private String email;

    private UserRole role;
}
