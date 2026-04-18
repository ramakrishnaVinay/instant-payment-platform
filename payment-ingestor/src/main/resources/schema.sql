CREATE TABLE IF NOT EXISTS account (
    account_id VARCHAR(255) PRIMARY KEY,
    account_name VARCHAR(255),
    account_type VARCHAR(255),
    status VARCHAR(255),
    currency VARCHAR(255),
    opened_date VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS payments (
    payment_id VARCHAR(255) PRIMARY KEY,
    debit_account_id VARCHAR(255),
    credit_account_id VARCHAR(255),
    amount DECIMAL(19,2),
    currency VARCHAR(10),
    reference VARCHAR(255),
    timestamp TIMESTAMP
);
