--liquibase formatted sql

--changeset qirsam:1
CREATE TABLE IF NOT EXISTS author
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    firstname   VARCHAR(64) NOT NULL,
    lastname    VARCHAR(64) NOT NULL,
    birth_date  DATE,
    description text
);
--rollback DROP TABLE author;

--changeset qirsam:2
CREATE TABLE IF NOT EXISTS book
(
    id          BIGSERIAL                                    NOT NULL PRIMARY KEY,
    title       VARCHAR(255)                                 NOT NULL,
    author_id   INT REFERENCES author (id) ON DELETE CASCADE NOT NULL,
    genre       VARCHAR(32),
    description text
);
--rollback DROP TABLE book;