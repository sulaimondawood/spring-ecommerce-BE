package com.dawood.e_commerce.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeReq->authorizeReq.requestMatchers("/auth/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(cors->cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return  configuration.getAuthenticationManager();
    }

    private CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration cors = new CorsConfiguration();

        cors.setAllowCredentials(true);
        cors.setAllowedHeaders(List.of("*"));
        cors.setAllowedMethods(List.of("*"));
        cors.setAllowedOrigins(List.of("http://localhost:3000","http://localhost:3001"));
        cors.setExposedHeaders(List.of("Authorization"));
        cors.setMaxAge(3600l);

        UrlBasedCorsConfigurationSource urlBasedCors = new UrlBasedCorsConfigurationSource();
        urlBasedCors.registerCorsConfiguration("/**",cors);
        return urlBasedCors;
    }

}
