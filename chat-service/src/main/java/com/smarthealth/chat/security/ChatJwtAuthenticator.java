package com.smarthealth.chat.security;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class ChatJwtAuthenticator {

	private static final SecretKey SIGNING_KEY = Keys.hmacShaKeyFor(
			"clave_super_secreta_de_256_bits!".getBytes(StandardCharsets.UTF_8));

	public Authentication authenticate(String authorization) {
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Se requiere un token Bearer");
		}

		Claims claims = Jwts.parserBuilder()
				.setSigningKey(SIGNING_KEY)
				.build()
				.parseClaimsJws(authorization.substring(7))
				.getBody();
		String rol = claims.get("rol", String.class);
		String identity = switch (rol == null ? "" : rol) {
			case "ADMIN" -> "admin";
			case "DOCTOR" -> {
				Integer idDoctor = claims.get("idDoctor", Integer.class);
				if (idDoctor == null) throw new IllegalArgumentException("La cuenta no tiene doctor asociado");
				yield "doctor:" + idDoctor;
			}
			case "RECEPCIONISTA" -> "recepcionista:" + claims.getSubject().trim().toLowerCase();
			default -> throw new IllegalArgumentException("El rol no tiene acceso al chat");
		};

		return new UsernamePasswordAuthenticationToken(
				identity,
				authorization.substring(7),
				List.of(new SimpleGrantedAuthority("ROLE_" + rol)));
	}
}
