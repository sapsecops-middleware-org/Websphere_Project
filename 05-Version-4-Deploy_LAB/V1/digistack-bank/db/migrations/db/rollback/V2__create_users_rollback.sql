-- V2__create_users_rollback.sql
-- Rolls back V2__create_users.sql by dropping the users table entirely.

DROP TABLE IF EXISTS users;