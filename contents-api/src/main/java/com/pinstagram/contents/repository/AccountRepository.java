package com.pinstagram.contents.repository;

import com.pinstagram.domain.entity.auth.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
}
