package com.nilufer.minibank.service;

import com.nilufer.minibank.dto.AccountDetailResponse;
import com.nilufer.minibank.dto.AccountRequest;
import com.nilufer.minibank.dto.AccountResponse;
import com.nilufer.minibank.dto.SearchAccountRequest;
import com.nilufer.minibank.exception.DuplicateValueException;
import com.nilufer.minibank.exception.NotFoundNorValidException;
import com.nilufer.minibank.model.Account;
import com.nilufer.minibank.model.User;
import com.nilufer.minibank.repository.AccountRepository;
import com.nilufer.minibank.repository.TransactionRepository;
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
    private final TransactionRepository transactionRepository;

    public List<AccountResponse> getAllAccounts() {
        User user = getCurrentUser();
        return accountRepository.findAllByUser_Id(user.getId())
                .stream()
                .map(AccountResponse::of)
                .toList();
    }

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

        return new AccountResponse(savedAccount.getId(), savedAccount.getName(), savedAccount.getNumber());
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

    public AccountResponse updateAccount(UUID id, AccountRequest accountRequest) {
        User user = getCurrentUser();

        //get account infos for updating
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundNorValidException("Account not found"));

        //if you are not this account's user, you shall NOT PASS :D
        if (!account.getUser().getId().equals(user.getId())) {
            throw new NotFoundNorValidException("You are not allowed to update this account");
        }

        account.setName(accountRequest.getAccountName());
        Account updatedAccount = accountRepository.save(account);

        return new AccountResponse(updatedAccount.getId(), updatedAccount.getName(), updatedAccount.getNumber());
    }

    public String deleteAccount(UUID id) {
        User user = getCurrentUser();

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundNorValidException("Account not found"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new NotFoundNorValidException("You are not allowed to delete this account");
        }
        boolean hasTransactions = transactionRepository.existsByFrom_Id(id) || transactionRepository.existsByTo_Id(id);

        if (hasTransactions) {
            throw new NotFoundNorValidException(
                    "This account has related transactions and cannot be deleted."
            );
        }
        accountRepository.delete(account);

        return "Deleted successfully";
    }


    public AccountDetailResponse getAccountDetails(UUID id) {
        User user = getCurrentUser();

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundNorValidException("Account not found"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new NotFoundNorValidException("You are not allowed to see this account's details");
        }
        return AccountDetailResponse.builder()
                .id(account.getId())
                .accountName(account.getName())
                .accountNumber(account.getNumber())
                .balance(account.getBalance())
                .createdDate(account.getCreatedAt())
                .build();
    }

    //to make jpa repository work, i made incoming null variables into empty string
    private String blankParameter(String parameter) {
        return Optional.ofNullable(parameter).orElse("");
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
