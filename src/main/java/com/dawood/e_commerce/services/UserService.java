package com.dawood.e_commerce.services;

import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.exceptions.UserNotFoundException;
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
        String username = jwtUtils.extractUsername(jwt);
        return getUserByEmail(username);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException());
    }

//    public List<US>

}
