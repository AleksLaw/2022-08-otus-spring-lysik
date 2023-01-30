package otus.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.dao.BookDao;
import otus.model.Book;
import otus.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDAO;

    @HystrixCommand(commandKey = "keyBook")
    @Override
    public Book saveBook(Book book) {
        return bookDAO.save(book);
    }

    @HystrixCommand(commandKey = "keyBook")
    @Override
    public Book getBook(long id) {
        return bookDAO.findById(id).orElse(null);
    }

    @HystrixCommand(commandKey = "keyBook")
    @Override
    public List<Book> getAllBook() {
        return bookDAO.findAll();
    }

    @HystrixCommand(commandKey = "keyBook")
    @Override
    public Book updateBook(Book book) {
        return bookDAO.save(book);
    }

    @HystrixCommand(commandKey = "keyBook")
    @Override
    public void deleteBook(Book book) {
        bookDAO.deleteById(book.getId());
    }
}
