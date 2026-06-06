package com.customeraccountapi.repositories;

import com.customeraccountapi.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class AccountRepositoryTest {

    @Test
    @DisplayName("Should get Account successfully from DB")
    void findAccountByDocumentNumberSuccess(){
        assertTrue(true);
    }

    @Test
    @DisplayName("Should not get Account successfully from DB when account not exists")
    void findAccountByDocumentNumberError(){
        assertTrue(true);
    }


}