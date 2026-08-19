package com.frameworktutor.backend.auth.service;

import java.util.Locale;

import com.frameworktutor.backend.auth.dto.RegistrationRequest;
import com.frameworktutor.backend.auth.dto.RegistrationResponse;
import com.frameworktutor.backend.auth.exception.EmailAlreadyRegisteredException;
import com.frameworktutor.backend.user.domain.AuthProvider;
import com.frameworktutor.backend.user.domain.User;
import com.frameworktutor.backend.user.domain.UserRole;
import com.frameworktutor.backend.user.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public RegistrationService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Transactional
    public RegistrationResponse register(RegistrationRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyRegisteredException();
        }

        User user = new User(
                email,
                passwordService.hash(request.password()),
                request.displayName().trim(),
                UserRole.LEARNER,
                AuthProvider.LOCAL,
                null,
                null);

        try {
            User savedUser = userRepository.saveAndFlush(user);
            return new RegistrationResponse(
                    savedUser.getId(),
                    savedUser.getEmail(),
                    savedUser.getDisplayName(),
                    savedUser.getRole(),
                    savedUser.getAuthProvider(),
                    savedUser.getCreatedAt());
        } catch (DataIntegrityViolationException exception) {
            throw new EmailAlreadyRegisteredException();
        }
    }
}
