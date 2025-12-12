package com.nilufer.minibank.dto;

import com.nilufer.minibank.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
public class AccountResponse {
    private UUID id;
    private String accountName;
    private String accountNumber;

    public static AccountResponse of(Account account) {
        return new AccountResponse(account.getId(),
                account.getName(),
                account.getNumber());
    }
}
