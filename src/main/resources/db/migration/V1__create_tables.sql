CREATE TABLE accounts (
    account_id BIGSERIAL PRIMARY KEY,
    document_number VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE operation_types (
    operation_type_id BIGINT PRIMARY KEY,
    description VARCHAR(100) NOT NULL
);

CREATE TABLE transactions (
    transaction_id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    operation_type_id BIGINT NOT NULL,
    amount NUMERIC(19,2) NOT NULL,
    event_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_transaction_account
        FOREIGN KEY (account_id)
        REFERENCES accounts(account_id),

    CONSTRAINT fk_transaction_operation_type
        FOREIGN KEY (operation_type_id)
        REFERENCES operation_types(operation_type_id)
);