package com.nilufer.minibank.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionRequest {
    @NotNull(message = "Sender account id is required")
    private UUID senderAccountId;

    @NotEmpty(message = "Recipient user's name is required. Please write the user's username")
    private String recipientUsername;

    @NotEmpty(message = "Recipient user's account number is required. Please write the user's account number")
    private String recipientAccountNumber;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than 0")
    private BigDecimal amount;
}
