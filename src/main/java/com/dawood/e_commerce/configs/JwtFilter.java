package com.dawood.e_commerce.configs;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dawood.e_commerce.services.CustomUserDetailsImpl;
import com.dawood.e_commerce.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsImpl customUserDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        try{
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            String jwt = authHeader.substring(7);
            String username = jwtUtils.parseToken(jwt).getSubject();

            System.out.println(username);

            UserDetails userDetails = customUserDetails.loadUserByUsername(username);

            logger.error(userDetails);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        }catch (Exception e){
            logger.error("Cannot set user authentication",e);
        }

        filterChain.doFilter(request,response);
    }
}
