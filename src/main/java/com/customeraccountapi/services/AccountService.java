    package com.customeraccountapi.services;

    import com.customeraccountapi.domain.Account;
    import com.customeraccountapi.dto.AccountCreateDTO;
    import com.customeraccountapi.dto.AccountResponseDTO;
    import com.customeraccountapi.exceptions.ConflictException;
    import com.customeraccountapi.exceptions.ResourceNotFoundException;
    import com.customeraccountapi.repositories.AccountRepository;
    import org.springframework.stereotype.Service;

    @Service
    public class AccountService {

        final AccountRepository accountRepository;

        public AccountService(AccountRepository accountRepository){
            this.accountRepository = accountRepository;
        }

        public AccountResponseDTO create(AccountCreateDTO dto) {
            if(accountRepository
                    .findAccountByDocumentNumber(dto.getDocumentNumber())
                    .isPresent()) {
                throw new ConflictException("Document already exists");
            }
            Account account = Account.builder()
                    .documentNumber(dto.getDocumentNumber())
                    .build();

            Account savedAccount = accountRepository.save(account);

            return toResponseDTO(savedAccount);
        }

        public Account findById(Long accountId){
            return accountRepository.findById(accountId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Account not found"));
        }

        private AccountResponseDTO toResponseDTO(Account account) {
            return new AccountResponseDTO(
                    account.getAccountId(),
                    account.getDocumentNumber()
            );
        }
    }
