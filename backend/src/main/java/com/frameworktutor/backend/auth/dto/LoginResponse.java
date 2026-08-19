package com.frameworktutor.backend.auth.dto;

import java.time.Instant;

import com.frameworktutor.backend.user.domain.UserRole;

public record LoginResponse(
        String tokenType,
        String accessToken,
        Instant expiresAt,
        Long userId,
        String email,
        String displayName,
        UserRole role) {
}
