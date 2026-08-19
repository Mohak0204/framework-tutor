package com.frameworktutor.backend.auth.service;

public interface PasswordService {

    String hash(String rawPassword);

    boolean matches(String rawPassword, String passwordHash);
}
