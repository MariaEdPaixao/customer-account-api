package com.customeraccountapi.repositories;

import com.customeraccountapi.domain.Account;
import com.customeraccountapi.dto.AccountCreateDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class AccountRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("Should get Account successfully from DB")
    void findAccountByDocumentNumberSuccess(){
       String document = "12345678900";
       this.createAccount(new AccountCreateDTO(document));

        Optional<Account> result = accountRepository.findAccountByDocumentNumber(document);
        assertTrue(result.isPresent());
        assertEquals(document, result.get().getDocumentNumber());
    }

    @Test
    @DisplayName("Should not get Account successfully from DB when account not exists")
    void findAccountByDocumentNumberError(){
        Optional<Account> result =
                accountRepository.findAccountByDocumentNumber("99999999999");

        assertTrue(result.isEmpty());    }

    private void createAccount(AccountCreateDTO createDTO) {
        Account account = Account.builder()
                .documentNumber(createDTO.getDocumentNumber())
                .build();

        entityManager.persist(account);
        entityManager.flush();
    }
}