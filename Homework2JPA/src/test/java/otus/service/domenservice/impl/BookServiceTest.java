package otus.service.domenservice.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.dao.BookDao;
import otus.model.Author;
import otus.model.Book;
import otus.model.Genre;
import otus.service.domenservice.AuthorService;
import otus.service.domenservice.BookService;
import otus.service.domenservice.GenreService;
import otus.service.ioservice.IOService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        expected = new Book("test", author, genre, null);
    }

    @Test
    void saveBook() {
        when(ioService.getString()).thenReturn("test");
        when(ioService.getLong()).thenReturn(1L);
        when(authorService.getAuthor(1L)).thenReturn(author);
        when(genreService.getGenre(1L)).thenReturn(genre);
        when(bookDao.save(expected)).thenReturn(expected);
        Book actual = bookService.saveBook(expected);
        assertEquals(expected, actual);
    }

    @Test
    void getBook() {
        when(ioService.getLong()).thenReturn(1L);
        when(bookDao.getById(1L)).thenReturn(Optional.of(expected));
        Book actual = bookService.getBook(1L);
        assertEquals(expected, actual);
    }

    @Test
    void getAllBook() {
        when(bookDao.getAll()).thenReturn(Collections.singletonList(expected));
        List<Book> allBook = bookService.getAllBook();
        assertEquals(1, allBook.size());
        assertEquals(expected, allBook.get(0));
    }

    @Test
    void updateBook() {
        expected.setId(1L);
        when(ioService.getLong()).thenReturn(1L);
        when(authorService.getAuthor(1L)).thenReturn(author);
        when(genreService.getGenre(1L)).thenReturn(genre);
        when(ioService.getString()).thenReturn("test");
        when(ioService.getLong()).thenReturn(1L);
        when(bookDao.update(expected)).thenReturn(expected);
        when(bookDao.getById(1L)).thenReturn(Optional.of(expected));
        Book actual = bookService.updateBook(expected);
        assertEquals(expected, actual);
    }

    @Test
    void deleteBook() {
        when(ioService.getLong()).thenReturn(1L);
        expected.setId(1L);
        when(bookDao.getById(1L)).thenReturn(Optional.of(expected));
        bookService.deleteBook(expected);
        verify(bookDao, times(1)).deleteById(1L);
    }
}