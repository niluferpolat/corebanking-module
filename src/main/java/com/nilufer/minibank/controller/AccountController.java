package com.nilufer.minibank.controller;

import com.nilufer.minibank.dto.AccountRequest;
import com.nilufer.minibank.dto.AccountResponse;
import com.nilufer.minibank.dto.SearchAccountRequest;
import com.nilufer.minibank.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> addAccount(@Valid @RequestBody AccountRequest accountRequest) {
        return ResponseEntity.ok(accountService.addAccount(accountRequest));
    }

    // The assessment used the same endpoint for both creation and search, so a separate /search endpoint was added to avoid duplication.
    @PostMapping("/search")
    public ResponseEntity<List<AccountResponse>> getAccountResponses(@Valid @RequestBody SearchAccountRequest request) {
        return ResponseEntity.ok(accountService.searchMyAccounts(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable UUID id, @Valid @RequestBody AccountRequest accountRequest) {
        return ResponseEntity.ok(accountService.updateAccount(id, accountRequest));
    }

}
