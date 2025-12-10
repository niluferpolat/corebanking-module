package com.nilufer.minibank.controller;

import com.nilufer.minibank.dto.AuthResponse;
import com.nilufer.minibank.dto.LoginRequest;
import com.nilufer.minibank.dto.RegisterRequest;
import com.nilufer.minibank.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
