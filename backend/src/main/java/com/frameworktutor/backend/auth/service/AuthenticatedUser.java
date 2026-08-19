package com.frameworktutor.backend.auth.service;

import com.frameworktutor.backend.user.domain.UserRole;

public record AuthenticatedUser(Long id, String email, UserRole role) {
}
