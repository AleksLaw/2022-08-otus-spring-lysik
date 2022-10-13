package otus.dao;


import otus.model.Author;

import java.util.List;

public interface AuthorDao {
    long count();

    long insert(Author author);

    long update(Author author);

    Author getById(long id);

    List<Author> getAll();

    long deleteById(long id);
}
