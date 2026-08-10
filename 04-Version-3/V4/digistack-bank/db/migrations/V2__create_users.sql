-- V2__create_users.sql
-- Creates the users table for Version 2 (Login & Session).
-- Password hashing: SHA-256 with per-user random salt.
-- Salt is not secret - it just needs to be unique per user to defeat
-- rainbow-table attacks and ensure identical passwords hash differently.

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(64) NOT NULL,
    salt VARCHAR(32) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Seed user for testing Login/Logout in Sprint 2.
-- Plaintext password for this seed user is: Test@1234
-- (Documented here ONLY because this is a seed/test row in a lab
-- migration script - a real production migration would NEVER document
-- a plaintext password anywhere, including comments.)
INSERT INTO users (username, password_hash, salt)
VALUES (
    'testuser1',
    '4c7b3a8fb9e428599fb04998b0f08228112195552568f9bb057e8e8dc22566e1',
    '7c9815e1a9a06846f91a14fa2ae60e0c'
);