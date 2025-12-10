package com.nilufer.minibank.repository;

import com.nilufer.minibank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFrom_IdOrTo_Id(UUID accountId1, UUID accountId2);
}
