package com.smarthealth.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import com.smarthealth.chat.security.ChatJwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final ChatJwtFilter chatJwtFilter;

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
					.requestMatchers(HttpMethod.GET, "/chat/mensajes").authenticated()
					.requestMatchers(HttpMethod.POST, "/chat/mensajes/sistema").permitAll()
					.requestMatchers("/chat", "/chat/**").permitAll()
					.anyRequest().authenticated())
			.addFilterBefore(chatJwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
