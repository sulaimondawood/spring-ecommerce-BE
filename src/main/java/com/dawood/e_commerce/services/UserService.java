package com.dawood.e_commerce.services;

import com.dawood.e_commerce.dtos.response.UserResponseDto;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.enums.UserRole;
import com.dawood.e_commerce.exceptions.UserNotFoundException;
import com.dawood.e_commerce.mapper.UserMapper;
import com.dawood.e_commerce.repository.UserRepository;
import com.dawood.e_commerce.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public User getUserByJwtToken(String jwt){
        String token = jwtUtils.extractTokenFromHeader(jwt);
        String username = jwtUtils.extractUsername(token);
        return getUserByEmail(username);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException());
    }

    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAll().stream().map(UserMapper::toDTO).toList();
    }

    public User getUserByEmailAndRole(String jwt, UserRole role){
        String token = jwtUtils.extractTokenFromHeader(jwt);
        String username = jwtUtils.extractUsername(token);

        return userRepository.findByEmailAndRole(username,role)
                .orElseThrow(()->new UserNotFoundException());

    }

}
