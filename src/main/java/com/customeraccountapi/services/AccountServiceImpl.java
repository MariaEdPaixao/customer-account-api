    package com.customeraccountapi.services;

    import com.customeraccountapi.domain.Account;
    import com.customeraccountapi.dto.AccountCreateDTO;
    import com.customeraccountapi.dto.AccountResponseDTO;
    import com.customeraccountapi.exceptions.ConflictException;
    import com.customeraccountapi.exceptions.ResourceNotFoundException;
    import com.customeraccountapi.repositories.AccountRepository;
    import org.springframework.stereotype.Service;

    @Service
    public class AccountServiceImpl implements AccountService {

        final AccountRepository accountRepository;

        public AccountServiceImpl(AccountRepository accountRepository){
            this.accountRepository = accountRepository;
        }

        @Override
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
        @Override
        public AccountResponseDTO  findById(Long accountId){
            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Account not found"));

            return toResponseDTO(account);
        }

        private AccountResponseDTO toResponseDTO(Account account) {
            return new AccountResponseDTO(
                    account.getAccountId(),
                    account.getDocumentNumber()
            );
        }
    }
