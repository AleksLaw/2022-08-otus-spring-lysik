package otus.dao;


import otus.model.Book;

import java.util.List;

public interface BookDao {
    long count();

    long insert(Book book);

    long update(Book book);

    Book getById(long id);

    List<Book> getAll();

    long deleteById(long id);
}
