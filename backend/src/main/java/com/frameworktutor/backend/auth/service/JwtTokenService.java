package com.frameworktutor.backend.auth.service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

import com.frameworktutor.backend.auth.config.JwtProperties;
import com.frameworktutor.backend.user.domain.User;
import com.frameworktutor.backend.user.domain.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService implements TokenService {

    private final JwtProperties jwtProperties;
    private final SecretKey signingKey;

    public JwtTokenService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secret());
        if (keyBytes.length < 32) {
            throw new IllegalStateException("JWT secret must contain at least 32 bytes");
        }
        signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public AccessToken createAccessToken(User user) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(jwtProperties.accessTokenTtl());
        if (!expiresAt.isAfter(issuedAt)) {
            throw new IllegalStateException("JWT access-token lifetime must be positive");
        }

        String token = Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiresAt))
                .signWith(signingKey)
                .compact();
        return new AccessToken(token, expiresAt);
    }

    @Override
    public Optional<AuthenticatedUser> authenticate(String token) {
        try {
            var claims = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return Optional.of(new AuthenticatedUser(
                    Long.valueOf(claims.getSubject()),
                    claims.get("email", String.class),
                    UserRole.valueOf(claims.get("role", String.class))));
        } catch (JwtException | IllegalArgumentException | NullPointerException exception) {
            return Optional.empty();
        }
    }
}
