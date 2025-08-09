package com.dawood.e_commerce.dtos.response;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private String Role;

}
