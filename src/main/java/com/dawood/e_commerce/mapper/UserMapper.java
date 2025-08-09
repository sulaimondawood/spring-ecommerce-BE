package com.dawood.e_commerce.mapper;

import com.dawood.e_commerce.dtos.response.SignupResponse;
import com.dawood.e_commerce.dtos.response.UserResponseDto;
import com.dawood.e_commerce.entities.User;

public class UserMapper {

    public static SignupResponse toSignupResponse(User user){

        SignupResponse res = new SignupResponse();
        res.setRole(user.getRole());
        res.setEmail(user.getEmail());
        res.setFullname(user.getFullname());
        res.setPhoneNumber(user.getPhoneNumber());
        res.setCreatedAt(user.getCreatedAt());
        res.setUpdatedAt(user.getUpdatedAt());

        return res;
    }

    public static UserResponseDto toDTO(User user){

         UserResponseDto response = UserResponseDto.builder()
                .email(user.getEmail())
                 .uuid(user.getUuid())
                .role(user.getRole())
                .phoneNumber(user.getPhoneNumber())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .build();

         return response;

    }
}
