package com.frameworktutor.backend.auth.service;

import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SpringSecurityPasswordService implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    public SpringSecurityPasswordService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String rawPassword) {
        return passwordEncoder.encode(Objects.requireNonNull(rawPassword, "rawPassword must not be null"));
    }

    @Override
    public boolean matches(String rawPassword, String passwordHash) {
        return passwordEncoder.matches(
                Objects.requireNonNull(rawPassword, "rawPassword must not be null"),
                Objects.requireNonNull(passwordHash, "passwordHash must not be null"));
    }
}
