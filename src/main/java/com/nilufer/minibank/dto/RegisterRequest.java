package com.nilufer.minibank.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 5, message = "Username must have at least 5 characters")
    private String username;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?=\\S+).{8,}$",
            message = "Password must contain upper, lower, digit, special char and be at least 8 characters."
    )
    private String password;
}
