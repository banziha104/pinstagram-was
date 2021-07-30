package com.pinstagram.authentication.repository;

import com.pinstagram.auth.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    boolean existsByEmail(String email);
    Optional<AccountEntity> findByEmailAndPassword(String email, String password);
}
