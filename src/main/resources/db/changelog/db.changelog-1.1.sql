--liquibase formatted sql

--changeset qirsam:1
ALTER TABLE book
    ADD CONSTRAINT book_title_key UNIQUE (title)
--rollback DROP TABLE book;