package com.dawood.e_commerce.services;

import com.dawood.e_commerce.dtos.response.SellerResponseDTO;
import com.dawood.e_commerce.entities.SellerProfile;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.enums.AccountStatus;
import com.dawood.e_commerce.enums.UserRole;
import com.dawood.e_commerce.exceptions.UserNotFoundException;
import com.dawood.e_commerce.mapper.SellerMapper;
import com.dawood.e_commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<SellerResponseDTO> getAllSellersByAccountStatus(AccountStatus status){
        return  userRepository.findByAccountStatus(status).stream()
                .map(SellerMapper::toDTO).collect(Collectors.toList());
    }

    public SellerResponseDTO getSellerById(UUID id){
        return userRepository.findById(id)
                .map(SellerMapper::toDTO)
                .orElseThrow(()->new UserNotFoundException("Seller account not found"));
    }

    public void setupSellerProfile(String jwt){

        Object principal  = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDetails userDetails = (UserDetails) principal;

        UserRole userRole = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(role->UserRole.valueOf(role.getAuthority()))
                .orElse(UserRole.CUSTOMER);

        User seller = userService.getUserByEmailAndRole(jwt, userRole);

        SellerProfile sellerProfile =new SellerProfile();

//        seller.setSellerProfile();
dd
    }



}
