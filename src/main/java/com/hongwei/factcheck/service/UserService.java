package com.hongwei.factcheck.service;

import com.hongwei.factcheck.dto.RegisterRequest;
import com.hongwei.factcheck.dto.UserResponse;
import com.hongwei.factcheck.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.hongwei.factcheck.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // If this method throws runtime exception, database operations rolled back
    @Transactional
    public UserResponse register (RegisterRequest request) {
        // Check if email already exists
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Email is already in use.");
                });

        // Hash raw password
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // Create and save new User entity
        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(hashedPassword)
                .build();

        User saved = userRepository.save(user);

        // Map to response DTO
        return UserResponse.builder()
                .id(saved.getId())
                .email(saved.getEmail())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}
