package com.smarthealth.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.smarthealth.chat.security.ChatStompAuthenticationInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

	private final ChatStompAuthenticationInterceptor authenticationInterceptor;

	@Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        config.enableSimpleBroker("/topic");

        config.setApplicationDestinationPrefixes("/app");
    }

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(authenticationInterceptor);
	}

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
