iNSERT INTO AUTHORS (NAME, SURNAME)
VALUES ('Karl', 'Marks'),
       ('Uzik', 'Stalin'),
       ('Vova', 'Lenin');

iNSERT INTO GENRES (NAME)
VALUES ('Roman'),
       ('Poem'),
       ('A note about your boy');

iNSERT INTO BOOKS (NAME, AUTHOR_ID, GENRE_ID)
VALUES ('Capital', 1, 1),
       ('Compositions', 2, 2);

INSERT INTO COMMENTS (COMMENT_TEXT, BOOK_ID)
VALUES ('so so book', 1),
       ('maybe', 1),
       ('terrible book', 1);