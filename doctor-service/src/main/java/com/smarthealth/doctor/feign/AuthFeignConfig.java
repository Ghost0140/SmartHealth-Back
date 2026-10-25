package com.smarthealth.doctor.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;

@Configuration
public class AuthFeignConfig {

	@Bean
	RequestInterceptor reenviarJwtAdministrador() {
		return requestTemplate -> {
			if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) {
				String authorization = attributes.getRequest().getHeader("Authorization");
				if (authorization != null && !authorization.isBlank()) {
					requestTemplate.header("Authorization", authorization);
				}
			}
		};
	}
}
