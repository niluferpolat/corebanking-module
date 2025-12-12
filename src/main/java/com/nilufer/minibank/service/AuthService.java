package com.nilufer.minibank.service;

import com.nilufer.minibank.dto.LoginRequest;
import com.nilufer.minibank.dto.RegisterRequest;
import com.nilufer.minibank.dto.AuthResponse;
import com.nilufer.minibank.exception.DuplicateValueException;
import com.nilufer.minibank.exception.NotFoundNorValidException;
import com.nilufer.minibank.model.User;
import com.nilufer.minibank.repository.UserRepository;
import com.nilufer.minibank.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateValueException("Username is already taken");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateValueException("Email already exists");
        }

        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(newUser);
        String token = jwtService.generateToken(newUser);
        return new AuthResponse(token, newUser.getUsername());
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmailOrUsername(loginRequest.getUsernameOrEmail());
        if (!userOptional.isPresent()) {
            throw new NotFoundNorValidException("User could not be found");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new NotFoundNorValidException("Invalid password");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getUsername());
    }
}
