package com.nilufer.minibank.controller;

import com.nilufer.minibank.dto.TransactionRequest;
import com.nilufer.minibank.model.Transaction;
import com.nilufer.minibank.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> initiateTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.transferTransaction(transactionRequest));
    }

    @GetMapping("/account/{accountId}")
    @ResponseBody
    public List<Transaction> getTransactionHistory(@PathVariable("accountId") UUID accountId) {
        return transactionService.showTransactionHistories(accountId);
    }
}
