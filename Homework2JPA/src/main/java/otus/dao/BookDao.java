package otus.dao;


import otus.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book save(Book book);

    Book update(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    long deleteById(long id);
}
