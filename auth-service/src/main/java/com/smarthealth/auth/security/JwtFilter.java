package com.smarthealth.auth.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smarthealth.auth.dto.UsuarioAutenticadoDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	
	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);

			try {
				Claims claims = jwtUtil.getClaims(token);
				
				UsuarioAutenticadoDto principal =
					    new UsuarioAutenticadoDto(
					        claims.get("idUsuario", Integer.class),
					        claims.getSubject(),
					        claims.get("rol", String.class),
					        claims.get("idDoctor", Integer.class)
				);

				if (SecurityContextHolder.getContext().getAuthentication() == null) {
				    UsernamePasswordAuthenticationToken auth =
				            new UsernamePasswordAuthenticationToken(
				                    principal,
				                    null,
				                    List.of(new SimpleGrantedAuthority("ROLE_" + principal.rol()))
				            );

				    SecurityContextHolder.getContext().setAuthentication(auth);
				}
			} catch (JwtException | IllegalArgumentException e) {
				SecurityContextHolder.clearContext();
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

}
