CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE book (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255) NOT NULL,
    publish_year INT
);

CREATE TABLE book_authors (
    book_id UUID NOT NULL,
    author VARCHAR(255) NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE
);

CREATE TABLE book_languages (
    book_id UUID NOT NULL,
    language VARCHAR(255) NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE
);
