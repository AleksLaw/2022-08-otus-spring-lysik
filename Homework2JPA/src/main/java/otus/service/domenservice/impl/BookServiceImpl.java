package otus.service.domenservice.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.dao.BookDao;
import otus.model.Book;
import otus.service.domenservice.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDAO;

    @Override
    @Transactional
    public Book saveBook(Book book) {
        return bookDAO.save(book);
    }

    @Override
    public Book getBook(long id) {
        return bookDAO.getById(id).orElse(null);
    }

    @Override
    public List<Book> getAllBook() {
        return bookDAO.getAll();
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        return bookDAO.update(book);
    }

    @Transactional
    @Override
    public void deleteBook(Book book) {
        bookDAO.deleteById(book.getId());
    }
}
