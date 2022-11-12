package otus.service.domenservice;


import otus.model.Author;

import java.util.List;

public interface AuthorService {

    Author saveAuthor(String name, String surname);

    Author getAuthor(long id);

    List<Author> getAllAuthor();

    Author updateAuthor(Author author);

    void deleteAuthor(Author author);
}
