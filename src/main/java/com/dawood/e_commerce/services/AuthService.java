package com.dawood.e_commerce.services;

import com.dawood.e_commerce.dtos.request.LoginRequest;
import com.dawood.e_commerce.dtos.request.SignupRequest;
import com.dawood.e_commerce.dtos.response.LoginReponse;
import com.dawood.e_commerce.dtos.response.SignupResponse;
import com.dawood.e_commerce.entities.Cart;
import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.enums.UserRole;
import com.dawood.e_commerce.exceptions.UserAlreadyExistsException;
import com.dawood.e_commerce.mapper.UserMapper;
import com.dawood.e_commerce.repository.CartRepository;
import com.dawood.e_commerce.repository.UserRepository;
import com.dawood.e_commerce.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final AuthenticationManager authenticationManager;
    private  final JwtUtils jwtUtils;

    public SignupResponse createUser(SignupRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException();
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullname(request.getFullname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());

        Cart cart = new Cart();
        Cart  userCart = cartRepository.save(cart);
        user.setCart(userCart);

        return UserMapper.toSignupResponse(userRepository.save(user));
    }

    public LoginReponse login(LoginRequest request){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                    request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtUtils.generateToken(userDetails);

        String role = authentication.getAuthorities()
                .stream()
                .map(auth->auth.getAuthority())
                .findFirst()
                .orElse(UserRole.CUSTOMER.name());

        LoginReponse response = new LoginReponse();

        response.setToken(token);
        response.setRole(role);

        return response;
    }

}
