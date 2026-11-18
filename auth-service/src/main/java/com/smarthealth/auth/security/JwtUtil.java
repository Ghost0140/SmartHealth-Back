package com.smarthealth.auth.security;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.smarthealth.auth.entity.UsuarioEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private static final long EXPIRATION = 1000L * 60L * 30L;
    private final SecretKey key;

    public JwtUtil(@org.springframework.beans.factory.annotation.Value("${security.jwt.secret-base64}") String secretBase64) {
        byte[] secret = Base64.getDecoder().decode(secretBase64);
        if (secret.length < 32) {
            throw new IllegalArgumentException("La clave JWT debe tener al menos 32 bytes (256 bits)");
        }
        this.key = Keys.hmacShaKeyFor(secret);
    }
    public String generateToken(UsuarioEntity usuario) {
        return Jwts.builder()
                .setSubject(usuario.getCorreo())
                .claim("idUsuario", usuario.getIdUsuario())
                .claim("rol", usuario.getRol().getNombre())
                .claim("idDoctor", usuario.getIdDoctor())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
