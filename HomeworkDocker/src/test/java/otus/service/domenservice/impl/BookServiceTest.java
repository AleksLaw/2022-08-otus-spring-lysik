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
import otus.service.AuthorService;
import otus.service.BookService;
import otus.service.GenreService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceTest {

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
        when(authorService.getAuthor(1L)).thenReturn(author);
        when(genreService.getGenre(1L)).thenReturn(genre);
        when(bookDao.save(expected)).thenReturn(expected);
        Book actual = bookService.saveBook(expected);
        assertEquals(expected, actual);
    }

    @Test
    void getBook() {
        when(bookDao.findById(1L)).thenReturn(Optional.of(expected));
        Book actual = bookService.getBook(1L);
        assertEquals(expected, actual);
    }

    @Test
    void getAllBook() {
        when(bookDao.findAll()).thenReturn(Collections.singletonList(expected));
        List<Book> allBook = bookService.getAllBook();
        assertEquals(1, allBook.size());
        assertEquals(expected, allBook.get(0));
    }

    @Test
    void updateBook() {
        expected.setId(1L);
        when(authorService.getAuthor(1L)).thenReturn(author);
        when(genreService.getGenre(1L)).thenReturn(genre);
        when(bookDao.save(expected)).thenReturn(expected);
        when(bookDao.findById(1L)).thenReturn(Optional.of(expected));
        Book actual = bookService.updateBook(expected);
        assertEquals(expected, actual);
    }

    @Test
    void deleteBook() {
        expected.setId(1L);
        when(bookDao.findById(1L)).thenReturn(Optional.of(expected));
        bookService.deleteBook(expected);
        verify(bookDao, times(1)).deleteById(1L);
    }
}