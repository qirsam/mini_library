--liquibase formatted sql

--changeset qirsam:1
CREATE TABLE IF NOT EXISTS users_book
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    book_id BIGINT      NOT NULL REFERENCES book (id) ON DELETE CASCADE,
    status  VARCHAR(32) NOT NULL,
    UNIQUE (user_id, book_id)
)
--rollback DROP TABLE author;