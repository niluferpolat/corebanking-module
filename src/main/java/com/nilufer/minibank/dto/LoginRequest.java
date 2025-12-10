package com.nilufer.minibank.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {

    @NotEmpty(message = "Please fill this field with your username or email")
    private String usernameOrEmail;

    @NotEmpty(message = "Please fill this field with your password")
    private String password;
}
