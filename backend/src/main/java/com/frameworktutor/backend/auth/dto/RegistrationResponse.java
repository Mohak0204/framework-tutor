package com.frameworktutor.backend.auth.dto;

import java.time.Instant;

import com.frameworktutor.backend.user.domain.AuthProvider;
import com.frameworktutor.backend.user.domain.UserRole;

public record RegistrationResponse(
        Long id,
        String email,
        String displayName,
        UserRole role,
        AuthProvider authProvider,
        Instant createdAt) {
}
