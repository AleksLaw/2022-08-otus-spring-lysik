package otus.service;

import otus.model.Book;

import java.util.List;

public interface BookService {
    long saveBook();

    Book getBook();

    Book getBookById(Long id);

    List<Book> getAllBook();

    Long updateBook();

    long countBook();

    long deleteBookById(long id);

    long deleteBook();
}
