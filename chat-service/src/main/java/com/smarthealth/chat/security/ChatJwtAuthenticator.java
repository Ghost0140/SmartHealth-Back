package com.smarthealth.chat.security;

import java.util.Base64;
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

	private final SecretKey signingKey;

	public ChatJwtAuthenticator(
			@org.springframework.beans.factory.annotation.Value("${security.jwt.secret-base64}") String secretBase64) {
		byte[] secret = Base64.getDecoder().decode(secretBase64);
		if (secret.length < 32) {
			throw new IllegalArgumentException("La clave JWT debe tener al menos 32 bytes (256 bits)");
		}
		this.signingKey = Keys.hmacShaKeyFor(secret);
	}

	public Authentication authenticate(String authorization) {
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Se requiere un token Bearer");
		}

		Claims claims = Jwts.parserBuilder()
				.setSigningKey(signingKey)
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
