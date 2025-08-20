package com.eventbooking.user_service.controller;

import com.eventbooking.user_service.dto.LoginRequest;
import com.eventbooking.user_service.dto.RegisterRequest;
import com.eventbooking.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        return userService.login(request)
                .map(user -> ResponseEntity.ok("Login successful"))
                .orElse(ResponseEntity.badRequest().body("Invalid credentials"));
    }
}