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