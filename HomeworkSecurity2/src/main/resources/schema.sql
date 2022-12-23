DROP TABLE IF EXISTS COMMENTS;
DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS GENRES;
DROP TABLE IF EXISTS acl_sid;
DROP TABLE IF EXISTS acl_class;
DROP TABLE IF EXISTS acl_entry;
DROP TABLE IF EXISTS acl_object_identity;

CREATE TABLE AUTHORS
(
    ID      BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME    VARCHAR(255),
    SURNAME VARCHAR(255)
);

CREATE TABLE GENRES
(
    ID   BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255)
);

CREATE TABLE BOOKS
(
    ID        BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME      VARCHAR(255),
    AUTHOR_ID BIGINT REFERENCES AUTHORS (id),
    GENRE_ID  BIGINT REFERENCES GENRES (id)
);

CREATE TABLE COMMENTS
(
    ID           BIGINT AUTO_INCREMENT PRIMARY KEY,
    COMMENT_TEXT VARCHAR(255),
    BOOK_ID      BIGINT NOT NULL REFERENCES BOOKS (id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS acl_sid
(
    id        bigint(20)   NOT NULL AUTO_INCREMENT,
    principal tinyint(1)   NOT NULL,
    sid       varchar(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_1 (sid,principal)
);
CREATE TABLE IF NOT EXISTS acl_class
(
    id    bigint(20)   NOT NULL AUTO_INCREMENT,
    class varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_2 (class)
);
CREATE TABLE IF NOT EXISTS acl_object_identity
(
    id                 bigint(20) NOT NULL AUTO_INCREMENT,
    object_id_class    bigint(20) NOT NULL,
    object_id_identity bigint(20) NOT NULL,
    parent_object      bigint(20) DEFAULT NULL,
    owner_sid          bigint(20) DEFAULT NULL,
    entries_inheriting tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
);

CREATE TABLE IF NOT EXISTS acl_entry
(
    id                  bigint(20) NOT NULL AUTO_INCREMENT,
    acl_object_identity bigint(20) NOT NULL,
    ace_order           int(11)    NOT NULL,
    sid                 bigint(20) NOT NULL,
    mask                int(11)    NOT NULL,
    granting            tinyint(1) NOT NULL,
    audit_success       tinyint(1) NOT NULL,
    audit_failure       tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id);
ALTER TABLE acl_entry
    ADD FOREIGN KEY (sid) REFERENCES acl_sid (id);
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);