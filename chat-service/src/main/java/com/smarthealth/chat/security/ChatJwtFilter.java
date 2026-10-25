package com.smarthealth.chat.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatJwtFilter extends OncePerRequestFilter {

	private final ChatJwtAuthenticator authenticator;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");
		if (authorization != null) {
			try {
				Authentication authentication = authenticator.authenticate(authorization);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (RuntimeException exception) {
				SecurityContextHolder.clearContext();
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token de chat inválido");
				return;
			}
		}
		filterChain.doFilter(request, response);
	}
}
