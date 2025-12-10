package com.nilufer.minibank.controller;

import com.nilufer.minibank.dto.AccountRequest;
import com.nilufer.minibank.dto.AccountResponse;
import com.nilufer.minibank.model.Account;
import com.nilufer.minibank.model.User;
import com.nilufer.minibank.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
     private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> addAccount(@Valid @RequestBody AccountRequest accountRequest){
        return ResponseEntity.ok(accountService.addAccount(accountRequest));
    }
}
