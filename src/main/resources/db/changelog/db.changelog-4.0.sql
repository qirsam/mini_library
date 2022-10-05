--liquibase formatted sql

--changeset qirsam:1
ALTER TABLE book
ADD COLUMN image VARCHAR(64);

--changeset qirsam:2
ALTER TABLE author
ADD COLUMN image VARCHAR(64);
