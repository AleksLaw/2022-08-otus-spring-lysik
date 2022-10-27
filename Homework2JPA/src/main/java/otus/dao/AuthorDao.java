package otus.dao;


import otus.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Author save(Author author);

    Author update(Author author);

    Optional<Author> getById(long id);

    List<Author> getAll();

    long deleteById(long id);
}
