-- V3__create_accounts.sql
-- Creates the accounts table for Version 3 (Basic Transaction).
-- balance uses NUMERIC (exact decimal), never FLOAT/DOUBLE - floating
-- point cannot represent decimal fractions exactly and is unsafe for
-- any monetary value.

CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    balance NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_accounts_users FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Seed account, linked to testuser1 (id=1, created in V2), starting
-- balance 1000.00 - enough to comfortably test both Deposit and
-- Withdraw (including a deliberate over-withdrawal negative test).
INSERT INTO accounts (user_id, account_number, balance)
VALUES (1, 'DSB-ACC-0001', 1000.00);