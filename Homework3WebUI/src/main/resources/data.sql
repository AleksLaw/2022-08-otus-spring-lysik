INSERT INTO AUTHORS (NAME, SURNAME)
VALUES ('Karl', 'Marks'),
       ('Uzik', 'Stalin'),
       ('Vova', 'Lenin'),
       ('Fridrih', 'Engels');

INSERT INTO GENRES (NAME)
VALUES ('Roman'),
       ('Poem'),
       ('Verse'),
       ('A note about your boy');

INSERT INTO BOOKS (NAME, AUTHOR_ID, GENRE_ID)
VALUES ('Capital', 1, 1),
       ('Compositions', 2, 2),
       ('Imperialism', 3, 3),
       ('German spy', 4, 4);

INSERT INTO COMMENTS (COMMENT_TEXT, BOOK_ID)
VALUES ('so so book', 1),
       ('maybe', 1),
       ('terrible book', 2),
       ('good book', 3);