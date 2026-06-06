package com.customeraccountapi.repositories;

import com.customeraccountapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByDocumentNumber(String documentNumber);
}
