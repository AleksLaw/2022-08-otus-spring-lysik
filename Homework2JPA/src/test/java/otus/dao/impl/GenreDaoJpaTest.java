package otus.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import otus.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {

    @Autowired
    private GenreDaoJpa genreDaoJpa;

    @Test
    void insert() {
        Genre genre = new Genre("test");
        long before = genreDaoJpa.getAll().size();
        Genre save = genreDaoJpa.save(genre);
        long after = genreDaoJpa.getAll().size();
        assertThat(before).isLessThan(after);
        assertThat(save.getId()).isEqualTo(4);
        Optional<Genre> genreById = genreDaoJpa.getById(save.getId());
        Genre actual = genreById.orElse(null);
        assertNotNull(actual);
        assertSame(genre.getName(), actual.getName());
    }

    @Test
    void update() {
        Genre genre = new Genre(2L, "testUp");
        genreDaoJpa.update(genre);
        Optional<Genre> genreById = genreDaoJpa.getById(2L);
        Genre actual = genreById.orElse(null);
        assertNotNull(actual);
        assertThat(actual).isEqualTo(genre);
    }

    @Test
    void getById() {
        Optional<Genre> genreById = genreDaoJpa.getById(1L);
        Genre actual = genreById.orElse(null);
        assertNotNull(actual);
        assertThat(actual.getName()).isEqualTo("Roman");
    }

    @Test
    void getAll() {
        List<Genre> all = genreDaoJpa.getAll();
        assertThat(all).contains(new Genre(1L, "Roman"));
        assertThat(all).contains(new Genre(2L, "Poem"));
        assertThat(all).contains(new Genre(3L, "A note about your boy"));
        assertThat(all.size()).isEqualTo(3);
    }

    @Test
    void deleteById() {
        long before = genreDaoJpa.getAll().size();
        genreDaoJpa.deleteById(3L);
        long after = genreDaoJpa.getAll().size();
        assertThat(before).isGreaterThan(after);
        Optional<Genre> actual = genreDaoJpa.getById(3L);
        assertEquals(Optional.empty(), actual);
    }
}