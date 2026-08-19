package com.frameworktutor.backend.auth.service;

import java.util.Locale;

import com.frameworktutor.backend.auth.dto.LoginRequest;
import com.frameworktutor.backend.auth.dto.LoginResponse;
import com.frameworktutor.backend.auth.exception.InvalidCredentialsException;
import com.frameworktutor.backend.user.domain.User;
import com.frameworktutor.backend.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public LoginService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(email, request.password()));
        } catch (AuthenticationException exception) {
            throw new InvalidCredentialsException();
        }

        User user = userRepository.findByEmail(email).orElseThrow(InvalidCredentialsException::new);
        AccessToken accessToken = tokenService.createAccessToken(user);
        return new LoginResponse(
                "Bearer",
                accessToken.value(),
                accessToken.expiresAt(),
                user.getId(),
                user.getEmail(),
                user.getDisplayName(),
                user.getRole());
    }
}
