package otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.dao.AuthorDao;
import otus.model.Author;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthorServiceTest {
    @MockBean
    private IOService ioService;
    @MockBean
    private AuthorDao authorDAO;
    @Autowired
    private AuthorService authorService;
    private Author expected;

    @BeforeEach
    public void setUp() {
        expected = new Author("test", "test");
    }

    @Test
    void saveAuthor() {
        when(ioService.getString()).thenReturn("test");
        when(authorDAO.insert(expected)).thenReturn(4L);
        when(authorDAO.getById(4L)).thenReturn(expected);
        long actual = authorService.saveAuthor();
        assertEquals(4L, actual);
    }

    @Test
    void getAllAuthor() {
        when(authorDAO.getAll()).thenReturn(Collections.singletonList(expected));
        List<Author> allAuthor = authorService.getAllAuthor();
        assertEquals(1, allAuthor.size());
        assertEquals(expected, allAuthor.get(0));
    }

    @Test
    void getAuthor() {
        when(ioService.getLong()).thenReturn(1L);
        when(authorDAO.getById(1L)).thenReturn(expected);
        Author actual = authorService.getAuthor();
        assertEquals(expected, actual);
    }

    @Test
    void getAuthorById() {
        when(authorDAO.getById(1L)).thenReturn(expected);
        Author actual = authorService.getAuthorById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void updateAuthor() {
        when(ioService.getString()).thenReturn("test");
        when(ioService.getLong()).thenReturn(1L);
        expected.setId(1L);
        when(authorDAO.update(expected)).thenReturn(1L);
        when(authorDAO.getAll()).thenReturn(Collections.singletonList(expected));
        when(authorDAO.getById(1L)).thenReturn(expected);
        long actual = authorService.updateAuthor();
        assertEquals(1L, actual);
    }

    @Test
    void countAuthor() {
        when(authorDAO.count()).thenReturn(3L);
        long actual = authorService.countAuthor();
        assertEquals(3, actual);
    }

    @Test
    void deleteAuthor() {
        when(ioService.getLong()).thenReturn(1L);
        when(authorDAO.deleteById(1L)).thenReturn(1L);
        expected.setId(1L);
        when(authorDAO.getAll()).thenReturn(Collections.singletonList(expected));
        long actual = authorService.deleteAuthor();
        assertEquals(1, actual);
    }

    @Test
    void deleteAuthorById() {
        when(authorDAO.deleteById(1L)).thenReturn(1L);
        long actual = authorService.deleteAuthorById(1L);
        assertEquals(1, actual);
    }

    @Test
    public void getOrSaveAuthorTestGet() {
        when(ioService.getLong()).thenReturn(1L);
        when(authorDAO.getById(1L)).thenReturn(expected);
        when(authorDAO.getAll()).thenReturn(Collections.singletonList(expected));
        Author actual = authorService.getOrSaveAuthor();
        assertEquals(expected, actual);
    }

    @Test
    public void getOrSaveAuthorTestSave() {
        when(ioService.getString()).thenReturn("test");
        when(authorDAO.getById(1L)).thenReturn(expected);
        when(authorDAO.insert(expected)).thenReturn(1L);
        Author actual = authorService.getOrSaveAuthor();
        assertEquals(expected, actual);
    }
}