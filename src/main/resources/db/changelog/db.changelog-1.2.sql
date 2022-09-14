--liquibase formatted sql

--changeset qirsam:1
ALTER TABLE book
    DROP CONSTRAINT book_title_key;
--rollback DROP TABLE book;