package com.kolekta.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    /** HS256 exige une clé d'au moins 256 bits (32 octets) ; en dessous, l'application ne démarre pas. */
    static final int SECRET_MIN_BYTES = 32;

    private final SecretKey key;
    private final long expirationMs;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration:3600000}") long expirationMs) {
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < SECRET_MIN_BYTES) {
            // Le message ne contient jamais le secret lui-même
            throw new IllegalStateException("JWT_SECRET trop court : " + SECRET_MIN_BYTES
                    + " octets minimum (voir .env.example pour en générer un)");
        }
        this.key = Keys.hmacShaKeyFor(bytes);
        this.expirationMs = expirationMs;
    }

    public String generate(Long userId, String role, String userType) {
        return Jwts.builder()
                // Identifiant unique du jeton : permet de le révoquer à la déconnexion
                .id(UUID.randomUUID().toString())
                .subject(String.valueOf(userId))
                .claim("role", role)
                .claim("userType", userType)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValid(String token) {
        try {
            parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getJti(String token) {
        return parse(token).getId();
    }

    public Instant getExpiration(String token) {
        return parse(token).getExpiration().toInstant();
    }

    public Long getUserId(String token) {
        return Long.parseLong(parse(token).getSubject());
    }

    public String getRole(String token) {
        return parse(token).get("role", String.class);
    }

    public String getUserType(String token) {
        return parse(token).get("userType", String.class);
    }
}
