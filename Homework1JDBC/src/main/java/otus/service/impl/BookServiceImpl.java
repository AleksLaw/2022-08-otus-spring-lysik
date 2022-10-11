package otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.dao.BookDao;
import otus.model.Author;
import otus.model.Book;
import otus.model.Genre;
import otus.service.AuthorService;
import otus.service.BookService;
import otus.service.GenreService;
import otus.service.IOService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    public static final String INPUT_ID_BOOK = "Введите Ид книги";
    public static final String SAVE_BOOK = "Сохранение книги";
    public static final String BOOK_COUNT = "Сейчас у нас количество книг = ";
    public static final String BOOK_WITH_ID = "Книга с ид = ";
    public static final String DELETED = " удалена";
    public static final String INPUT_BOOK_NAME = "Ведите название книги";
    private final BookDao bookDAO;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final IOService ioService;

    @Override
    public long saveBook() {
        ioService.outputString(SAVE_BOOK);
        long id = bookDAO.insert(createBook());
        ioService.outputString(getBookById(id).toString());
        return id;
    }

    @Override
    public Book getBook() {
        getAllBook();
        ioService.outputString(INPUT_ID_BOOK);
        Book book = bookDAO.getById(ioService.getLong());
        ioService.outputString(book.toString());
        return book;
    }

    @Override
    public Book getBookById(Long id) {
        return bookDAO.getById(id);
    }

    @Override
    public List<Book> getAllBook() {
        List<Book> all = bookDAO.getAll();
        all.forEach(s -> ioService.outputString(s.toString()));
        return all;
    }

    @Override
    public Long updateBook() {
        Long id = checkId();
        Book book = createBook();
        book.setId(id);
        long rez = bookDAO.update(book);
        ioService.outputString(getBookById(id).toString());
        return rez;
    }

    @Override
    public long countBook() {
        long count = bookDAO.count();
        ioService.outputString(BOOK_COUNT + count);
        return count;
    }

    @Override
    public long deleteBookById(long id) {
        long rez = bookDAO.deleteById(id);
        ioService.outputString(BOOK_WITH_ID + id + DELETED);
        return rez;
    }

    @Override
    public long deleteBook() {
        return deleteBookById(checkId());
    }

    private Book createBook() {
        ioService.outputString(INPUT_BOOK_NAME);
        String name = ioService.getString();
        Author orSaveAuthor = authorService.getOrSaveAuthor();
        Genre orSaveGenre = genreService.getOrSaveGenre();
        return new Book(name, orSaveAuthor, orSaveGenre);
    }

    private Long checkId() {
        Long id;
        List<Long> collect = getAllBook().stream().map(Book::getId).collect(Collectors.toList());
        do {
            ioService.outputString(INPUT_ID_BOOK);
            id = ioService.getLong();
        } while (!collect.contains(id));
        return id;
    }
}
