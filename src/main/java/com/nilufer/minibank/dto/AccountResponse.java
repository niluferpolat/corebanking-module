package com.nilufer.minibank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountResponse {
    private String accountName;
    private String accountNumber;
    private BigDecimal balance;
}
