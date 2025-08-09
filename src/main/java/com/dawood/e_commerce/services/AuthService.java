package com.dawood.e_commerce.services;

import com.dawood.e_commerce.dtos.request.LoginRequest;
import com.dawood.e_commerce.dtos.request.SignupRequest;
import com.dawood.e_commerce.dtos.response.LoginResponse;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
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

    public LoginResponse login(LoginRequest request){

        try {
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

            LoginResponse response = new LoginResponse();

            response.setToken(token);
            response.setRole(role);

            return response;
        } catch (BadCredentialsException ex){
            log.error("UsernamePasswordException Caught {}",ex.getMessage());
            throw new BadCredentialsException("Username or password is incorrect");
        }


    }

}
