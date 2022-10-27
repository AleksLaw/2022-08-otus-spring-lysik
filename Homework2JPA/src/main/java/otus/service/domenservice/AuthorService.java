package otus.service.domenservice;


import otus.model.Author;

import java.util.List;

public interface AuthorService {
    Author saveAuthor();

    Author getAuthor();

    List<Author> getAllAuthor();

    Author updateAuthor();

    long deleteAuthor();
}
