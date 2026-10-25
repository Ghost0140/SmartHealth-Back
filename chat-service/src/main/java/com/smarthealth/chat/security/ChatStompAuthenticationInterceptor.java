package com.smarthealth.chat.security;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatStompAuthenticationInterceptor implements ChannelInterceptor {

	private final ChatJwtAuthenticator authenticator;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
			String authorization = accessor.getFirstNativeHeader("Authorization");
			Authentication authentication = authenticator.authenticate(authorization);
			accessor.setUser(authentication);
		} else if (accessor != null
				&& (StompCommand.SUBSCRIBE.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand()))) {
			Authentication authentication = (Authentication) accessor.getUser();
			if (authentication == null) {
				throw new AccessDeniedException("Autenticación requerida para el chat");
			}
			String destination = accessor.getDestination();
			if (StompCommand.SEND.equals(accessor.getCommand()) && !"/app/mensaje".equals(destination)) {
				throw new AccessDeniedException("Destino de chat no permitido");
			}
			if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
				String rol = authentication.getAuthorities().iterator().next().getAuthority().replaceFirst("^ROLE_", "").toLowerCase();
				String identity = authentication.getName();
				if (!("/topic/" + identity).equals(destination) && !("/topic/" + rol).equals(destination)) {
					throw new AccessDeniedException("Solo puedes recibir mensajes dirigidos a tu cuenta");
				}
			}
		}
		return message;
	}
}
