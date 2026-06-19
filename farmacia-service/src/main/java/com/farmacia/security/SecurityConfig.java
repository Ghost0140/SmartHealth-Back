package com.farmacia.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // Gestión de medicamentos: solo ADMIN o FARMACIA
                        .requestMatchers(HttpMethod.POST, "/api/medicamentos").hasAnyRole("ADMIN", "FARMACIA")
                        .requestMatchers(HttpMethod.PUT, "/api/medicamentos/**").hasAnyRole("ADMIN", "FARMACIA")
                        .requestMatchers(HttpMethod.DELETE, "/api/medicamentos/**").hasAnyRole("ADMIN", "FARMACIA")
                        .requestMatchers(HttpMethod.GET, "/api/medicamentos/**").hasAnyRole("ADMIN", "FARMACIA", "DOCTOR")
                        // Recetas: DOCTOR genera, FARMACIA y ADMIN consultan
                        .requestMatchers(HttpMethod.POST, "/api/recetas").hasAnyRole("DOCTOR", "FARMACIA")
                        .requestMatchers(HttpMethod.GET, "/api/recetas/**").hasAnyRole("ADMIN", "FARMACIA", "DOCTOR")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
