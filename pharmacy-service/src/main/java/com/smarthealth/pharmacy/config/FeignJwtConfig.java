package com.smarthealth.pharmacy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;

@Configuration
public class FeignJwtConfig {
	
	@Bean
    RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes)
                            RequestContextHolder.getRequestAttributes();

            if (attributes == null) {
                return;
            }

            String authorization =
                    attributes
                            .getRequest()
                            .getHeader("Authorization");

            if (authorization != null) {
                requestTemplate.header(
                        "Authorization",
                        authorization
                );
            }
        };
    }

}
