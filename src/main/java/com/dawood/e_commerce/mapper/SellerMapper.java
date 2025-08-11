package com.dawood.e_commerce.mapper;

import com.dawood.e_commerce.dtos.response.SellerResponseDTO;
import com.dawood.e_commerce.entities.User;

public class SellerMapper {

    public static SellerResponseDTO toDTO(User user){
        return SellerResponseDTO.builder()
                .uuid(user.getUuid())
                .fullname(user.getFullname())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .isEmailVerified(user.getIsEmailVerified())
                .accountStatus(user.getAccountStatus())
                .build();
    }

}
