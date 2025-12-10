package com.nilufer.minibank.service;

import com.nilufer.minibank.dto.AccountRequest;
import com.nilufer.minibank.dto.AccountResponse;
import com.nilufer.minibank.exception.DuplicateValueException;
import com.nilufer.minibank.exception.NotFoundException;
import com.nilufer.minibank.model.Account;
import com.nilufer.minibank.model.User;
import com.nilufer.minibank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountResponse addAccount(AccountRequest accountRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            throw new NotFoundException("User not authenticated");
        }

        Object principal = auth.getPrincipal();
        if (!(principal instanceof User)) {
            throw new NotFoundException("Invalid user details");
        }

        Optional<Account> accountOptional = accountRepository.findByName(accountRequest.getAccountName());

        if (accountOptional.isPresent()) {
            throw new DuplicateValueException("This account already exists");
        }

        User user = (User) auth.getPrincipal();
        Account newAccount = Account.builder()
                .name(accountRequest.getAccountName())
                .user(user)
                .balance(BigDecimal.ZERO)
                .build();
        Account savedAccount = accountRepository.save(newAccount);

        return new AccountResponse(savedAccount.getName(), savedAccount.getNumber(), savedAccount.getBalance());
    }
}
