--liquibase formatted sql

--changeset qirsam:1
CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(64) NOT NULL UNIQUE,
    password   VARCHAR(128),
    birth_date DATE,
    firstname  VARCHAR(64),
    lastname   VARCHAR(64),
    role       VARCHAR(32)
);
--rollback DROP TABLE author;