package com.dawood.e_commerce.controllers;

import com.dawood.e_commerce.dtos.response.UserResponseDto;
import com.dawood.e_commerce.entities.Address;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.services.AddressService;
import com.dawood.e_commerce.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AddressService addressService;

    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(userService.getUserByJwtToken(jwt));
    }

    @DeleteMapping("/addresses")
    public ResponseEntity<Map<String, String>> deleteAllAddress() {

        Map<String, String> response = new HashMap<>();

        response.put("message", "Addresses deleted succesfully");

        addressService.deleteAllUserAddress();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAllUserAddress() {

        return new ResponseEntity<>(addressService.getUserAddresses(), HttpStatus.OK);

    }

}
