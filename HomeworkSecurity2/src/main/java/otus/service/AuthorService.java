package otus.service;


import otus.model.Author;

import java.util.List;

public interface AuthorService {

    Author saveAuthor(Author author);

    Author getAuthor(long id);

    List<Author> getAllAuthor();

    Author updateAuthor(Author author);

    void deleteAuthor(Author author);
}
