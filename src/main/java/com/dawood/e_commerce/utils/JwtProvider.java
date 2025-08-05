package com.dawood.e_commerce.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class JwtProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);
    @Value("${jwt.secret}")
    private String key;

    private SecretKey secretKey;

    @PostConstruct
    public void init(){
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    private SecretKey getSecretKey(){
        return this.secretKey;
    }


    public String generateToken(){
        return null;
    }

    public Claims parseToken(String jwt){
        try {
          return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();
        }catch (JwtException ex){
            log.error("Invalid JWT token", ex);
            throw new JwtException("Invalid JWT token");
        }
    }

    public String extractUsername(String jwt){
        return parseToken(jwt).getSubject();
    }

}
