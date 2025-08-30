package com.dawood.e_commerce.services;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dawood.e_commerce.entities.Address;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.exceptions.user.UserNotFoundException;
import com.dawood.e_commerce.repository.AddressRepository;
import com.dawood.e_commerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

  private final UserRepository userRepository;
  private final AddressRepository addressRepository;

  // public void deleteAllAddress() {

  // String email =
  // SecurityContextHolder.getContext().getAuthentication().getName();

  // User user = userRepository.findByEmail(email)
  // .orElseThrow(() -> new UserNotFoundException("User does not exist"));

  // List<Address> addresses = user.getAddresses();

  // addressRepository.deleteAll(addresses);

  // }

  @Transactional
  public void deleteAllUserAddress() {
    // get logged-in user's email
    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("User does not exist"));

    List<Address> addresses = user.getAddresses();
    if (addresses.isEmpty()) {
      return; // nothing to delete
    }

    // Remove addresses from user entity (to keep both sides consistent)
    user.getAddresses().clear();
    userRepository.save(user);

    // Delete addresses from repository
    addressRepository.deleteAll(addresses);
  }

  public List<Address> getUserAddresses() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("User does not exist"));

    return user.getAddresses();
  }

}
