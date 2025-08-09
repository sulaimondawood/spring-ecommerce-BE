package com.dawood.e_commerce.utils;

import com.dawood.e_commerce.services.CustomUserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);
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


    public String generateToken(UserDetails userDetails){

        Map<String,Object> claims = new HashMap<>();

        claims.put("role", userDetails.getAuthorities()
                .stream()
                .map(authority->authority.getAuthority())
                .toList());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(getSecretKey())
                .compact();
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
