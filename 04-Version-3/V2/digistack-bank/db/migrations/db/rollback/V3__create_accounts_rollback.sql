-- V3__create_accounts_rollback.sql
-- Rolls back V3__create_accounts.sql by dropping the accounts table.

DROP TABLE IF EXISTS accounts;