package com.nilufer.minibank.repository;

import com.nilufer.minibank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFrom_IdOrTo_Id(UUID accountId1, UUID accountId2);
    boolean existsByFrom_Id(UUID accountId);
    boolean existsByTo_Id(UUID accountId);
}
