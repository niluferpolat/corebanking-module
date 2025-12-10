package com.nilufer.minibank.dto;

import com.nilufer.minibank.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountResponse {
    private String accountName;
    private String accountNumber;
    private BigDecimal balance;

    public static AccountResponse of(Account account) {
        return new AccountResponse(account.getName(),
                account.getNumber(),
                account.getBalance());
    }
}
