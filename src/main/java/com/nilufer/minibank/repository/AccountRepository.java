package com.nilufer.minibank.repository;

import com.nilufer.minibank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByName(String name);

    List<Account> findByNameContainingIgnoreCaseAndNumberContainingAndUser_Id(String name, String number, UUID id);

    List<Account> findAllByUser_Id(UUID id);

    @Query("SELECT a FROM Account a WHERE LOWER(a.user.username) = LOWER(:username) AND a.number = :number")
    Optional<Account> findByUserUsernameIgnoreCaseAndNumber(@Param("username") String username, @Param("number") String number);
}
