package com.dawood.e_commerce.services;

import com.dawood.e_commerce.dtos.response.SellerResponseDTO;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.enums.UserRole;
import com.dawood.e_commerce.mapper.SellerMapper;
import com.dawood.e_commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final CustomUserDetailsImpl customUserDetails;


    public List<SellerResponseDTO> getAllSellers(){
        return userRepository.findByRole(UserRole.SELLER).stream()
                .map(SellerMapper::toDTO)
                .toList();
    }

    public void setupSellerProfile(String jwt){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        UserRole userRole = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(role->UserRole.valueOf(role.getAuthority()))
                .orElse(UserRole.CUSTOMER);

        User user = userService.getUserByEmailAndRole(jwt, userRole);

    }

}
