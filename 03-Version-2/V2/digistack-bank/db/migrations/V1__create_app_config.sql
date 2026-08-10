-- V1__create_app_config.sql
-- Creates the app_config table used by Version 1's live PostgreSQL read test.
-- Per STD naming: table = app_config, primary key = id.

CREATE TABLE app_config (
    id SERIAL PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL UNIQUE,
    config_value VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Seed one row so the Home page has something real to read.
INSERT INTO app_config (config_key, config_value)
VALUES ('welcome_message', 'DigiStack Bank is live - Version 1');