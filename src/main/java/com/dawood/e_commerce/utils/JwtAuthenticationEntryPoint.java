package com.dawood.e_commerce.utils;

import com.dawood.e_commerce.dtos.response.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),errorDetails);
    }
}
