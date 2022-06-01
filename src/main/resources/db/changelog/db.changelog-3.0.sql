--liquibase formatted sql

--changeset golov:1
ALTER TABLE users
ADD COLUMN image VARCHAR(64);

--changeset golov:2
ALTER TABLE users_aud
ADD COLUMN image VARCHAR(64);