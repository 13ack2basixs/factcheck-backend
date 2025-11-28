package com.hongwei.factcheck.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

// Do not expose User entity directly in APIs, control what is being sent
@Data
@Builder // Generate constructors, getters and setters for us
public class UserResponse {
    private Long id;
    private String email;
    private LocalDateTime createdAt;
}
