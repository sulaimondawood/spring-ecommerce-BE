package com.dawood.e_commerce.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dawood.e_commerce.entities.SellerReport;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.exceptions.user.UserNotFoundException;
import com.dawood.e_commerce.repository.SellerReportRepository;
import com.dawood.e_commerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerReportService {

  private final SellerReportRepository sellerReportRepository;
  private final UserRepository userRepository;

  public SellerReport getSellerReport() {

    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    User currentUser = userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("User not found"));

    SellerReport sellerReport = sellerReportRepository.findBySellerUuid(currentUser.getUuid());

    return sellerReport;
  }

}
