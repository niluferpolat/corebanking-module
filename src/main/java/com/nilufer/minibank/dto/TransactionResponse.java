package com.nilufer.minibank.dto;

import com.nilufer.minibank.model.Transaction;
import com.nilufer.minibank.model.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private TransactionStatus transactionStatus;

    public static TransactionResponse of(Transaction transaction) {
        String fromInfo = transaction.getFrom().getName() + "-" + transaction.getFrom().getNumber();
        String toInfo = transaction.getTo().getName() + "-" + transaction.getTo().getNumber();
        return new TransactionResponse(fromInfo, toInfo, transaction.getAmount()
                , transaction.getTransactionDate(), transaction.getStatus());
    }
}
