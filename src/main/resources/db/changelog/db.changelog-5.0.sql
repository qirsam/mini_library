--liquibase formatted sql

--changeset qirsam:1
ALTER TABLE book
DROP COLUMN image;

--changeset qirsam:2
ALTER TABLE author
DROP COLUMN image;
