package com.nilufer.minibank.controller;

import com.nilufer.minibank.dto.TransactionRequest;
import com.nilufer.minibank.model.Transaction;
import com.nilufer.minibank.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> initiateTransaction(@Valid @RequestBody TransactionRequest transactionRequest){
     return ResponseEntity.ok(transactionService.transferTransaction(transactionRequest));
    }
}
