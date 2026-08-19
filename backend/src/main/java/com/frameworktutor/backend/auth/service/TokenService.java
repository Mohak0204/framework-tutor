package com.frameworktutor.backend.auth.service;

import com.frameworktutor.backend.user.domain.User;

import java.util.Optional;

public interface TokenService {

    AccessToken createAccessToken(User user);

    Optional<AuthenticatedUser> authenticate(String token);
}
