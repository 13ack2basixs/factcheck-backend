package com.hongwei.factcheck.controller;

import com.hongwei.factcheck.dto.RegisterRequest;
import com.hongwei.factcheck.dto.UserResponse;
import com.hongwei.factcheck.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // Sets HTTP code that endpoint returns
    public UserResponse register(@RequestBody @Valid RegisterRequest request) {
        return userService.register(request);
    }
}
