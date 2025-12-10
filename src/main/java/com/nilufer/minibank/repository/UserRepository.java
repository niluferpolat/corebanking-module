package com.nilufer.minibank.repository;

import com.nilufer.minibank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email = :value OR u.username = :value")
    Optional<User> findByEmailOrUsername(String value);
}
