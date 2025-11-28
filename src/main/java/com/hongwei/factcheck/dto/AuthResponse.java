package com.hongwei.factcheck.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    // JWT string
    private String accessToken;

    // Bearer
    private String tokenType;
    private UserResponse user;
}


