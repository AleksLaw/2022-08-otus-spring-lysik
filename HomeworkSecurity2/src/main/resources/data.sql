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

INSERT INTO ACL_SID (ID, PRINCIPAL, SID)
VALUES (1, 1, 'u'),
       (2, 1, 'a'),
       (3, 1, 'k');

INSERT INTO ACL_CLASS (ID, CLASS)
VALUES (1, 'otus.model.mongo.Book');

INSERT INTO ACL_OBJECT_IDENTITY (ID, OBJECT_ID_CLASS, OBJECT_ID_IDENTITY, PARENT_OBJECT,
                                 OWNER_SID, ENTRIES_INHERITING)
VALUES (1, 1, 1, NULL, 3, 0),
       (2, 1, 2, NULL, 3, 0),
       (3, 1, 3, NULL, 3, 0),
       (4, 1, 4, NULL, 3, 0);

INSERT INTO ACL_ENTRY (ID, ACL_OBJECT_IDENTITY, ACE_ORDER, SID, MASK, GRANTING,
                       AUDIT_SUCCESS, AUDIT_FAILURE)
VALUES (1, 1, 1, 1, 1, 1, 1, 1),
       (2, 1, 2, 2, 1, 1, 1, 1),
       (3, 2, 1, 1, 1, 1, 1, 1),
       (4, 2, 2, 2, 1, 1, 1, 1),
       (5, 3, 1, 1, 1, 1, 1, 1),
       (6, 3, 2, 2, 1, 1, 1, 1),
       (7, 4, 1, 3, 1, 1, 1, 1);
