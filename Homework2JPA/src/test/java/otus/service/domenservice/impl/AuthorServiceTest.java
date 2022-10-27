package otus.service.domenservice.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.dao.AuthorDao;
import otus.model.Author;
import otus.service.domenservice.AuthorService;
import otus.service.ioservice.IOService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        when(authorDAO.save(expected)).thenReturn(expected);
        Author actual = authorService.saveAuthor();
        assertEquals(expected, actual);
    }

    @Test
    void getAuthor() {
        expected.setId(1L);
        when(ioService.getLong()).thenReturn(1L);
        when(authorDAO.getById(1L)).thenReturn(Optional.of(expected));
        Author actual = authorService.getAuthor();
        assertEquals(expected, actual);
    }

    @Test
    void getAllAuthor() {
        when(authorDAO.getAll()).thenReturn(Collections.singletonList(expected));
        List<Author> allAuthor = authorService.getAllAuthor();
        assertEquals(1, allAuthor.size());
        assertEquals(expected, allAuthor.get(0));
    }

    @Test
    void updateAuthor() {
        when(ioService.getString()).thenReturn("test");
        when(ioService.getLong()).thenReturn(1L);
        expected.setId(1L);
        when(authorDAO.update(expected)).thenReturn(expected);
        when(authorDAO.getById(1L)).thenReturn(Optional.of(expected));
        Author author = authorService.updateAuthor();
        assertEquals(expected, author);
    }

    @Test
    void deleteAuthor() {
        when(ioService.getLong()).thenReturn(1L);
        when(authorDAO.deleteById(1L)).thenReturn(1L);
        expected.setId(1L);
        when(authorDAO.getById(1L)).thenReturn(Optional.of(expected));
        long actual = authorService.deleteAuthor();
        assertEquals(1, actual);
    }
}