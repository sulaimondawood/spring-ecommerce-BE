package com.dawood.e_commerce.controllers;

import com.dawood.e_commerce.dtos.request.LoginRequest;
import com.dawood.e_commerce.dtos.request.SignupRequest;
import com.dawood.e_commerce.dtos.response.LoginReponse;
import com.dawood.e_commerce.dtos.response.SignupResponse;
import com.dawood.e_commerce.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<SignupResponse> createUser(@RequestBody @Valid SignupRequest request){

        SignupResponse res = authService.createUser(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<LoginReponse> login(@RequestBody @Valid LoginRequest request){
        return new ResponseEntity<>(authService.login(request),HttpStatus.OK);
    }

}
