package otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.dao.GenreDao;
import otus.model.Genre;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class GenreServiceTest {
    @MockBean
    private IOService ioService;
    @MockBean
    private GenreDao genreDAO;
    @Autowired
    private GenreService genreService;
    private Genre expected;

    @BeforeEach
    public void setUp() {
        expected = new Genre("test");
    }

    @Test
    void saveGenre() {
        when(ioService.getString()).thenReturn("test");
        when(genreDAO.insert(expected)).thenReturn(4L);
        when(genreDAO.getById(4L)).thenReturn(expected);
        long actual = genreService.saveGenre();
        assertEquals(4L, actual);
    }

    @Test
    void getAllGenre() {
        when(genreDAO.getAll()).thenReturn(Collections.singletonList(expected));
        List<Genre> allGenre = genreService.getAllGenre();
        assertEquals(1, allGenre.size());
        assertEquals(expected, allGenre.get(0));
    }

    @Test
    void getGenre() {
        when(ioService.getLong()).thenReturn(1L);
        when(genreDAO.getById(1L)).thenReturn(expected);
        Genre actual = genreService.getGenre();
        assertEquals(expected, actual);
    }

    @Test
    void getGenreById() {
        when(genreDAO.getById(1L)).thenReturn(expected);
        Genre actual = genreService.getGenreById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void updateGenre() {
        when(ioService.getString()).thenReturn("test");
        when(ioService.getLong()).thenReturn(1L);
        expected.setId(1L);
        when(genreDAO.update(expected)).thenReturn(1L);
        when(genreDAO.getAll()).thenReturn(Collections.singletonList(expected));
        when(genreDAO.getById(1L)).thenReturn(expected);
        long actual = genreService.updateGenre();
        assertEquals(1L, actual);
    }

    @Test
    void countGenre() {
        when(genreDAO.count()).thenReturn(3L);
        long actual = genreService.countGenre();
        assertEquals(3, actual);
    }

    @Test
    void deleteGenre() {
        when(ioService.getLong()).thenReturn(1L);
        when(genreDAO.deleteById(1L)).thenReturn(1L);
        Genre genre = new Genre(1L, "test");
        when(genreDAO.getAll()).thenReturn(Collections.singletonList(genre));
        long actual = genreService.deleteGenre();
        assertEquals(1, actual);
    }

    @Test
    void deleteGenreById() {
        when(genreDAO.deleteById(1L)).thenReturn(1L);
        long actual = genreService.deleteGenreById(1L);
        assertEquals(1, actual);
    }

    @Test
    public void getOrSaveGenreTestGet() {
        when(ioService.getLong()).thenReturn(1L);
        when(genreDAO.getById(1L)).thenReturn(expected);
        when(genreDAO.getAll()).thenReturn(Collections.singletonList(expected));
        Genre actual = genreService.getOrSaveGenre();
        assertEquals(expected, actual);
    }


    @Test
    public void getOrSaveGenreTestSave() {
        when(ioService.getString()).thenReturn("test");
        when(genreDAO.getById(1L)).thenReturn(expected);
        when(genreDAO.insert(expected)).thenReturn(1L);
        Genre actual = genreService.getOrSaveGenre();
        assertEquals(expected, actual);
    }
}