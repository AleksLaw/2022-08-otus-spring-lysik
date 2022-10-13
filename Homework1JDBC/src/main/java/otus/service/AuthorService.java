package otus.service;


import otus.model.Author;

import java.util.List;

public interface AuthorService {
    long saveAuthor();

    Author getAuthorById(long id);

    Author getAuthor();

    List<Author> getAllAuthor();

    Author getOrSaveAuthor();

    Long updateAuthor();

    long countAuthor();

    long deleteAuthorById(long id);

    long deleteAuthor();
}
