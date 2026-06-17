package com.smarthealth.citas_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
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
						.requestMatchers(HttpMethod.GET, "/api/citas/**").hasAnyRole("DOCTOR", "RECEPCIONISTA")
						.requestMatchers(HttpMethod.POST, "/api/citas").hasRole("RECEPCIONISTA")
						.requestMatchers(HttpMethod.PUT, "/api/citas/**").hasAnyRole("DOCTOR", "RECEPCIONISTA")
						.requestMatchers(HttpMethod.DELETE, "/api/citas/**").hasAnyRole("DOCTOR", "RECEPCIONISTA")

						.anyRequest().authenticated()
				)
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
