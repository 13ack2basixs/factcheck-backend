package com.hongwei.factcheck.service;

import com.hongwei.factcheck.dto.AuthResponse;
import com.hongwei.factcheck.dto.LoginRequest;
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
    private final JwtService jwtService;

    // Build UserResponse DTO
    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

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
        return toUserResponse(saved);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // Verify raw password from client vs hashed password in database
        boolean isMatch = passwordEncoder.matches(
                request.getPassword(), user.getPasswordHash()
        );

        if (!isMatch) throw new IllegalArgumentException(("Invalid email or password"));

        // Generate JWT
        String token = jwtService.generateToken(user);

        return AuthResponse.builder() // Build AuthResponse DTO
                .accessToken(token)
                .tokenType("Bearer")
                .user(toUserResponse(user))
                .build();
    }
}
