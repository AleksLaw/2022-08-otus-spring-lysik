package otus.service.domenservice.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.dao.GenreDao;
import otus.model.Genre;
import otus.service.GenreService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class GenreServiceTest {
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
        when(genreDAO.save(expected)).thenReturn(expected);
        Genre actual = genreService.saveGenre(expected);
        assertEquals(expected, actual);
    }

    @Test
    void getGenre() {
        expected.setId(1L);
        when(genreDAO.findById(1L)).thenReturn(Optional.of(expected));
        Genre actual = genreService.getGenre(1L);
        assertEquals(expected, actual);
    }

    @Test
    void getAllGenre() {
        when(genreDAO.findAll()).thenReturn(Collections.singletonList(expected));
        List<Genre> allGenre = genreService.getAllGenre();
        assertEquals(1, allGenre.size());
        assertEquals(expected, allGenre.get(0));
    }

    @Test
    void updateGenre() {
        expected.setId(1L);
        when(genreDAO.save(expected)).thenReturn(expected);
        when(genreDAO.findById(1L)).thenReturn(Optional.of(expected));
        Genre actual = genreService.updateGenre(expected);
        assertEquals(expected, actual);
    }

    @Test
    void deleteGenre() {
        expected.setId(1L);
        when(genreDAO.findById(1L)).thenReturn(Optional.of(expected));
        genreService.deleteGenre(expected);
        verify(genreDAO, times(1)).deleteById(1L);
    }

}