package com.nilufer.minibank.dto;

import lombok.Data;

@Data
public class SearchAccountRequest {
    private String accountName;
    private String accountNumber;
}
