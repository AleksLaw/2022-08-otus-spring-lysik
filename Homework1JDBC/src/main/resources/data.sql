iNSERT INTO AUTHORS (NAME, SURNAME)
VALUES ('Karl', 'Marks'),
       ('Uzik', 'Stalin'),
       ('Vova', 'Lenin'),
       ('Fridrih', 'Engels');

iNSERT INTO GENRES (NAME)
VALUES ('Roman'),
       ('Poem'),
       ('Verse'),
       ('A note about your boy');

iNSERT INTO BOOKS (NAME, AUTHOR_ID, GENRE_ID)
VALUES ('Capital', 1, 1),
       ('Compositions', 2, 2),
       ('Imperialism as the highest stage of capitalism', 3, 3),
       ('Interrogation plan for a German spy', 4, 4);