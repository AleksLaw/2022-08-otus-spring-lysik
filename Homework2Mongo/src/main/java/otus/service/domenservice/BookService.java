package otus.service.domenservice;

import otus.model.Book;

import java.util.List;

public interface BookService {

    Book saveBook(Book book);

    Book getBook(String id);

    List<Book> getAllBook();

    Book updateBook(Book book);

    void deleteBook(Book book);
}
