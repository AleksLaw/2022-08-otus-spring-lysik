package otus.service.domenservice.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.dao.BookDao;
import otus.model.Book;
import otus.service.domenservice.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDAO;

    @Override
    public Book saveBook(Book book) {
        return bookDAO.save(book);
    }

    @Override
    public Book getBook(String id) {
        return bookDAO.findById(id).orElse(null);
    }

    @Override
    public List<Book> getAllBook() {
        return bookDAO.findAll();
    }

    @Override
    public Book updateBook(Book book) {
        return bookDAO.save(book);
    }

    @Override
    public void deleteBook(Book book) {
        bookDAO.deleteById(book.getId());
    }
}
