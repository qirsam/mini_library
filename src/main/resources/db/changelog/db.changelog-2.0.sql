--liquibase formatted sql

--changeset qirsam:1
CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR(64) NOT NULL UNIQUE,
    password   VARCHAR(128),
    birth_date DATE,
    firstname  VARCHAR(64),
    lastname   VARCHAR(64),
    role       VARCHAR(32)
);
--rollback DROP TABLE author;

--changeset qirsam:2
CREATE TABLE IF NOT EXISTS users_book
(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE ,
    book_id BIGINT NOT NULL REFERENCES book (id) ON DELETE CASCADE ,
    status VARCHAR (32) NOT NULL ,
    UNIQUE (user_id, book_id)

)
