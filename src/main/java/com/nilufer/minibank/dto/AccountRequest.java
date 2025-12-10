package com.nilufer.minibank.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountRequest {
    @NotEmpty(message = "Account name is required")
    @Size(min = 5, message = "Account name must have at least 5 characters length")
    private String accountName;
}
