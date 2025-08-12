package com.dawood.e_commerce.services;

import com.dawood.e_commerce.dtos.request.SellerProfileDTO;
import com.dawood.e_commerce.dtos.response.SellerResponseDTO;
import com.dawood.e_commerce.entities.BankDetails;
import com.dawood.e_commerce.entities.BusinessDetails;
import com.dawood.e_commerce.entities.SellerProfile;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.enums.AccountStatus;
import com.dawood.e_commerce.enums.UserRole;
import com.dawood.e_commerce.exceptions.UserNotFoundException;
import com.dawood.e_commerce.mapper.SellerMapper;
import com.dawood.e_commerce.repository.UserRepository;
import com.dawood.e_commerce.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final CustomUserDetailsImpl customUserDetails;
    private final JwtUtils jwtUtils;


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

    public User setupSellerProfile(String jwt, SellerProfileDTO request){

        Object principal  = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDetails userDetails = (UserDetails) principal;

        UserRole userRole = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(role->UserRole.valueOf(role.getAuthority()))
                .orElse(UserRole.CUSTOMER);

        User seller = userService.getUserByEmailAndRole(jwt, userRole);

        SellerProfile sellerProfile =new SellerProfile();

        BankDetails bankDetails = new BankDetails();
        bankDetails.setBankName(request.getBankDetails().getBankName());
        bankDetails.setAccountNumber(request.getBankDetails().getAccountNumber());
        bankDetails.setAccountHolderName(request.getBankDetails().getAccountHolderName());

        BusinessDetails businessDetails = new BusinessDetails();
        businessDetails.setBusinessAddress(request.getBusinessDetails().getBusinessAddress());
        businessDetails.setBusinessBanner(request.getBusinessDetails().getBusinessBanner());
        businessDetails.setBusinessEmail(request.getBusinessDetails().getBusinessEmail());
        businessDetails.setBusinessName(request.getBusinessDetails().getBusinessName());
        businessDetails.setBusinessImage(request.getBusinessDetails().getBusinessImage());
        businessDetails.setBusinessNumber(request.getBusinessDetails().getBusinessNumber());

        sellerProfile.setBankDetails(bankDetails);
        sellerProfile.setBusinessDetails(businessDetails);

        sellerProfile.setSeller(seller);
        seller.setSellerProfile(sellerProfile);


        return userRepository.save(seller);
    }


    public User getSellerProfile(String jwt){
        String token = jwtUtils.extractTokenFromHeader(jwt);
        String email = jwtUtils.extractUsername(token);

        return userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException());
    }

    public BankDetails updateBankDetails(BankDetails bankDetailsDTO){

        SecurityContext context = SecurityContextHolder.getContext();

        String email = context.getAuthentication().getName();

        log.info(email);
        log.info("EMail from seller service");

        User seller = userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException());


        if(seller.getSellerProfile() == null){
            throw new IllegalStateException("Seller profile not found, please create a profile first");
        }

        BankDetails bankDetails = seller.getSellerProfile().getBankDetails();

        if(bankDetails == null){
            bankDetails = new BankDetails();
        }

        if(bankDetailsDTO.getBankName() != null && !bankDetailsDTO.getBankName().isBlank()){
            bankDetails.setBankName(bankDetailsDTO.getBankName());
        }

        if (bankDetailsDTO.getAccountNumber() != null && !bankDetailsDTO.getAccountNumber().isBlank()) {
            bankDetails.setAccountNumber(bankDetailsDTO.getAccountNumber());
        }

        if(bankDetailsDTO.getAccountHolderName() != null && !bankDetailsDTO.getAccountHolderName().isBlank()){
            bankDetails.setAccountHolderName(bankDetailsDTO.getAccountHolderName());
        }

        seller.getSellerProfile().
        setBankDetails(bankDetails);

        userRepository.save(seller);

        return bankDetails;

    }

    public BusinessDetails updateBusinessInfo(BusinessDetails businessDetailsRequest){

        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User seller = userService.getUserByEmail(username);

        if(seller.getSellerProfile() == null){
            throw new IllegalStateException("Seller profile not found, please create a profile first");
        }

        BusinessDetails businessDetails = seller.getSellerProfile().getBusinessDetails();

        if(businessDetails == null){
            businessDetails = new BusinessDetails();
        }

        if(businessDetailsRequest.getBusinessBanner() != null && !businessDetailsRequest.getBusinessBanner().isBlank()){
            businessDetails.setBusinessBanner(businessDetailsRequest.getBusinessBanner());
        }

        if(businessDetailsRequest.getBusinessEmail() != null && !businessDetailsRequest.getBusinessEmail().isBlank()){
            businessDetails.setBusinessEmail(businessDetailsRequest.getBusinessEmail());
        }

        if(businessDetailsRequest.getBusinessName() != null && !businessDetailsRequest.getBusinessName().isBlank()){
            businessDetails.setBusinessName(businessDetailsRequest.getBusinessName());
        }

        if(businessDetailsRequest.getBusinessNumber() != null && !businessDetailsRequest.getBusinessNumber().isBlank()){
            businessDetails.setBusinessNumber(businessDetailsRequest.getBusinessNumber());
        }

        if(businessDetailsRequest.getBusinessImage() != null && !businessDetailsRequest.getBusinessImage().isBlank()){
            businessDetails.setBusinessImage(businessDetailsRequest.getBusinessImage());
        }

        if(businessDetailsRequest.getBusinessAddress() != null && !businessDetailsRequest.getBusinessAddress().isBlank()){
            businessDetails.setBusinessAddress(businessDetailsRequest.getBusinessAddress());
        }

        seller.getSellerProfile().setBusinessDetails(businessDetails);

        userRepository.save(seller);

        return  businessDetails;
    }

}
