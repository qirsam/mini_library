--liquibase formatted sql

--changeset qirsam:1
CREATE TABLE author
(
    id        INTEGER NOT NULL,
    firstname VARCHAR(64) NOT NULL ,
    lastname  VARCHAR(64) NOT NULL ,
    birth_date DATE,
    description text,
    CONSTRAINT pk_author PRIMARY KEY (id)
);
--rollback DROP TABLE author;

--changeset qirsam:2
CREATE TABLE book
(
    id        BIGSERIAL NOT NULL,
    title     VARCHAR(255) NOT NULL ,
    author_id INT REFERENCES author (id) NOT NULL,
    genre   VARCHAR(32),
    description text,
    CONSTRAINT pk_book PRIMARY KEY (id)
);
--rollback DROP TABLE book;