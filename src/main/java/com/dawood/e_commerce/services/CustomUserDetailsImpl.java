package com.dawood.e_commerce.services;

import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.enums.UserRole;
import com.dawood.e_commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));

        return buildUserDetails(user.getEmail(),user.getPassword(),user.getRole());
    }

    private UserDetails buildUserDetails(String username, String password, UserRole role){
        if(role == null) role=UserRole.CUSTOMER;
        GrantedAuthority authority = new SimpleGrantedAuthority(role.name());

        return new org.springframework.security.core.userdetails.User(username,password,List.of(authority));
    }
}
