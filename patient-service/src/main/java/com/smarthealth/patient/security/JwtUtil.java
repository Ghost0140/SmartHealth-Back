package com.smarthealth.patient.security;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final SecretKey key;

    public JwtUtil(@org.springframework.beans.factory.annotation.Value("${security.jwt.secret-base64}") String secretBase64) {
        byte[] secret = Base64.getDecoder().decode(secretBase64);
        if (secret.length < 32) {
            throw new IllegalArgumentException("La clave JWT debe tener al menos 32 bytes (256 bits)");
        }
        this.key = Keys.hmacShaKeyFor(secret);
    }
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
