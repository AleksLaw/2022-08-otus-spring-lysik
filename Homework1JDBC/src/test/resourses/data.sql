iNSERT INTO AUTHORS (NAME, SURNAME)
VALUES ('Карл', 'Маркс'),
       ('Юзик', 'Сталин');

iNSERT INTO GENRES (NAME)
VALUES ('Роман'),
       ('Поэма');

iNSERT INTO BOOKS (NAME, AUTHOR_ID, GENRE_ID)
VALUES ('Капитал', 1, 1),
       ('Сочинения', 2, 2);