package otus.service.domenservice;

import otus.model.Book;

import java.util.List;

public interface BookService {
    Book saveBook();

    Book getBook();

    List<Book> getAllBook();

    Book updateBook();

    long deleteBook();

}
