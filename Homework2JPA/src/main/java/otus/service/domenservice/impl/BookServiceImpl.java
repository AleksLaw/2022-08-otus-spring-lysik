package otus.service.domenservice.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.dao.BookDao;
import otus.model.Author;
import otus.model.Book;
import otus.model.Genre;
import otus.service.domenservice.AuthorService;
import otus.service.domenservice.BookService;
import otus.service.domenservice.GenreService;
import otus.service.ioservice.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    public static final String INPUT_ID_BOOK = "Введите Ид книги";
    public static final String SAVE_BOOK = "Сохранение книги";
    public static final String BOOK_WITH_ID = "Книга с ид = ";
    public static final String DELETED = " удалена";
    public static final String INPUT_BOOK_NAME = "Введите название книги";
    public static final String NOT_DELETED = "Автор не может быть удален. Он является автором книги которая у нас есть";
    public static final String NOT_FIND = "Не найден";
    private final BookDao bookDAO;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final IOService ioService;

    @Override
    @Transactional
    public Book saveBook() {
        ioService.outputString(SAVE_BOOK);
        Book book = bookDAO.save(createBook());
        ioService.outputString(book.toString());
        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBook() {
        Book book = bookDAO.getById(getId()).orElse(null);
        if (book == null) {
            ioService.outputString(NOT_FIND);
            getAllBook();
        } else {
            ioService.outputString(book.toString());
        }
        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBook() {
        List<Book> allBooks = bookDAO.getAll();
        allBooks.forEach(book -> ioService.outputString(book.toString()));
        return allBooks;
    }

    @Override
    @Transactional
    public Book updateBook() {
        Long id = checkBook().getId();
        Book book = createBook();
        book.setId(id);
        Book update = bookDAO.update(book);
        ioService.outputString(update.toString());
        return update;
    }

    @Transactional
    @Override
    public long deleteBook() {
        Book checkBook = checkBook();
        long id = checkBook.getId();
        long rez = 0;
        try {
            rez = bookDAO.deleteById(id);
            ioService.outputString(BOOK_WITH_ID + id + DELETED);
        } catch (RuntimeException e) {
            ioService.outputString(NOT_DELETED);
        }
        return rez;
    }

    private Book createBook() {
        ioService.outputString(INPUT_BOOK_NAME);
        String name = ioService.getString();
        Author author = authorService.getAuthor();
        Genre genre = genreService.getGenre();
        return new Book(name, author, genre);
    }

    private long getId() {
        ioService.outputString(INPUT_ID_BOOK);
        return ioService.getLong();
    }

    private Book checkBook() {
        Book book;
        do {
            book = bookDAO.getById(getId()).orElse(null);
            if (book == null) {
                ioService.outputString(NOT_FIND);
                getAllBook();
            }
        } while (book == null);
        return book;
    }
}
