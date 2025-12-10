package com.nilufer.minibank.service;

import com.nilufer.minibank.dto.AccountRequest;
import com.nilufer.minibank.dto.AccountResponse;
import com.nilufer.minibank.dto.SearchAccountRequest;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountResponse addAccount(AccountRequest accountRequest) {
        User user = getCurrentUser();

        Optional<Account> accountOptional = accountRepository.findByName(accountRequest.getAccountName());
        if (accountOptional.isPresent()) {
            throw new DuplicateValueException("This account already exists");
        }

        Account newAccount = Account.builder()
                .name(accountRequest.getAccountName())
                .user(user)
                .balance(BigDecimal.ZERO)
                .build();
        Account savedAccount = accountRepository.save(newAccount);

        return new AccountResponse(savedAccount.getName(), savedAccount.getNumber(), savedAccount.getBalance());
    }

    //user can see own accounts with this jpa query
    public List<AccountResponse> searchMyAccounts(SearchAccountRequest searchAccountRequest) {
        User user = getCurrentUser();
        return accountRepository
                .findByNameContainingIgnoreCaseAndNumberContainingAndUser_Id(
                        blankParameter(searchAccountRequest.getAccountName()),
                        blankParameter(searchAccountRequest.getAccountNumber()),
                        user.getId()).stream()
                .map(AccountResponse::of).toList();

    }

    public String updateAccount(UUID id, AccountRequest accountRequest) {
        User user = getCurrentUser();

        //get account infos for updating
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        //if you are not this account's user, you shall NOT PASS :D
        if (!account.getUser().getId().equals(user.getId())) {
            throw new NotFoundException("You are not allowed to update this account");
        }

        account.setName(accountRequest.getAccountName());
        accountRepository.save(account);

        return "Updated successfully";
    }

    //to make jpa repository work, i made incoming null variables into empty string
    private String blankParameter(String parameter) {
        return Optional.ofNullable(parameter).orElse("");
    }

    //Check authentication and retrieve the authenticated user
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            throw new NotFoundException("User not authenticated");
        }

        Object principal = auth.getPrincipal();
        if (!(principal instanceof User)) {
            throw new NotFoundException("Invalid user details");
        }
        return (User) auth.getPrincipal();
    }
}
