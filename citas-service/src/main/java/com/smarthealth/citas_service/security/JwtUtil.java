package com.smarthealth.citas_service.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "mi_clave_secreta_muy_larga_para_seguridad_2026_auth_service";

	private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.verifyWith((SecretKey) key)
				.build()
				.parseSignedClaims(token);

			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String getRole(String token) {
        return getClaims(token)
                .get("role", String.class);
    }

}
