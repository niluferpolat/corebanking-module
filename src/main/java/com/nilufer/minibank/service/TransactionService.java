package com.nilufer.minibank.service;

import com.nilufer.minibank.dto.TransactionRequest;
import com.nilufer.minibank.exception.NotFoundNorValidException;
import com.nilufer.minibank.model.Account;
import com.nilufer.minibank.model.Transaction;
import com.nilufer.minibank.model.TransactionStatus;
import com.nilufer.minibank.model.User;
import com.nilufer.minibank.repository.AccountRepository;
import com.nilufer.minibank.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public Transaction transferTransaction(TransactionRequest transactionRequest) {
        User senderUser = getCurrentUser(); //fetch senderUser

        Account fromAccount = accountRepository.findById(transactionRequest.getSenderAccountId())
                .orElseThrow(() -> new NotFoundNorValidException("Sender account not found"));

        if (!fromAccount.getUser().getId().equals(senderUser.getId())) {
            throw new NotFoundNorValidException("You are not allowed to do this transaction");
        }

        Account recipientAccount = accountRepository
                .findByUserUsernameIgnoreCaseAndNumber(transactionRequest.getRecipientUsername(),
                        transactionRequest.getRecipientAccountNumber()).orElseThrow(() ->
                        new NotFoundNorValidException("Recipient's account not found"));


        //check sender user's balance status if balance is not enough, it throws error
        if (fromAccount.getBalance().compareTo(transactionRequest.getAmount()) < 0) {
            throw new NotFoundNorValidException("Insufficient balance");
        }

        //update balances
        fromAccount.setBalance(fromAccount.getBalance().subtract(transactionRequest.getAmount()));
        recipientAccount.setBalance(recipientAccount.getBalance().add(transactionRequest.getAmount()));

        //transaction object for creating a transaction
        Transaction transaction = Transaction.builder()
                .to(recipientAccount)
                .from(fromAccount)
                .amount(transactionRequest.getAmount())
                .build();

        try {
            accountRepository.save(fromAccount);
            accountRepository.save(recipientAccount);
            transaction.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new RuntimeException(e);
        }

        return transaction;
    }

    public List<Transaction> showTransactionHistories(UUID accountId) {
        User currentUser = getCurrentUser();

        //account exists or not
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundNorValidException("Account not found"));

        //authenticated user and account user is the same check
        if (!account.getUser().getId().equals(currentUser.getId())) {
            throw new NotFoundNorValidException("You are not allowed to view this account's transactions");
        }
        return transactionRepository.findByFrom_IdOrTo_Id(account.getId(), account.getId());
    }

    //Check authentication and retrieve the authenticated user
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            throw new NotFoundNorValidException("User not authenticated");
        }

        Object principal = auth.getPrincipal();
        if (!(principal instanceof User)) {
            throw new NotFoundNorValidException("Invalid user details");
        }
        return (User) auth.getPrincipal();
    }
}
