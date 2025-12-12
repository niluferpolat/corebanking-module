package com.nilufer.minibank.dto;

import com.nilufer.minibank.model.Account;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
public class AccountDetailResponse extends AccountResponse {

    private BigDecimal balance;
    private LocalDateTime createdDate;

    public static AccountDetailResponse of(Account account) {
        return AccountDetailResponse.builder()
                .id(account.getId())
                .accountName(account.getName())
                .accountNumber(account.getNumber())
                .balance(account.getBalance())
                .createdDate(account.getCreatedAt())
                .build();
    }
}
