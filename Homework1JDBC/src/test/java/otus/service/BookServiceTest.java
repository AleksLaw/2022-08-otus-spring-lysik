package otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.dao.BookDao;
import otus.model.Author;
import otus.model.Book;
import otus.model.Genre;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceTest {
    @MockBean
    private IOService ioService;
    @MockBean
    private BookDao bookDao;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @Autowired
    private BookService bookService;
    private Author author;
    private Genre genre;
    private Book expected;

    @BeforeEach
    public void setUp() {
        author = new Author(1L, "Karl", "Marks");
        genre = new Genre(1L, "Roman");
        expected = new Book("test", author, genre);
    }

    @Test
    void saveBook() {
        when(ioService.getString()).thenReturn("test");
        when(authorService.getOrSaveAuthor()).thenReturn(author);
        when(genreService.getOrSaveGenre()).thenReturn(genre);
        when(bookDao.insert(expected)).thenReturn(3L);
        when(bookDao.getById(3L)).thenReturn(expected);
        long actual = bookService.saveBook();
        assertEquals(3L, actual);
    }

    @Test
    void getAllBook() {
        when(bookDao.getAll()).thenReturn(Collections.singletonList(expected));
        List<Book> allBook = bookService.getAllBook();
        assertEquals(1, allBook.size());
        assertEquals(expected, allBook.get(0));
    }

    @Test
    void getBook() {
        when(ioService.getLong()).thenReturn(1L);
        when(bookDao.getById(1L)).thenReturn(expected);
        Book actual = bookService.getBook();
        assertEquals(expected, actual);
    }

    @Test
    void getBookById() {
        when(bookDao.getById(1L)).thenReturn(expected);
        Book actual = bookService.getBookById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void updateBook() {
        expected.setId(1L);
        when(authorService.getOrSaveAuthor()).thenReturn(author);
        when(genreService.getOrSaveGenre()).thenReturn(genre);
        when(ioService.getString()).thenReturn("test");
        when(ioService.getLong()).thenReturn(1L);
        when(bookDao.update(expected)).thenReturn(1L);
        when(bookDao.getAll()).thenReturn(Collections.singletonList(expected));
        when(bookDao.getById(1L)).thenReturn(expected);
        long actual = bookService.updateBook();
        assertEquals(1L, actual);
    }

    @Test
    void countBook() {
        when(bookDao.count()).thenReturn(3L);
        long actual = bookService.countBook();
        assertEquals(3, actual);
    }

    @Test
    void deleteBook() {
        expected.setId(1L);
        when(ioService.getLong()).thenReturn(1L);
        when(bookDao.deleteById(1L)).thenReturn(1L);
        when(bookDao.getAll()).thenReturn(Collections.singletonList(expected));
        long actual = bookService.deleteBook();
        assertEquals(1, actual);
    }

    @Test
    void deleteBookById() {
        when(bookDao.deleteById(1L)).thenReturn(1L);
        long actual = bookService.deleteBookById(1L);
        assertEquals(1, actual);
    }
}