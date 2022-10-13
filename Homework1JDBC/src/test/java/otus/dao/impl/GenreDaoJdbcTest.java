package otus.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import otus.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertSame;


@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    void count() {
        long count = genreDaoJdbc.count();
        genreDaoJdbc.getAll().forEach(System.out::println);
        assertThat(count).isEqualTo(3);
    }

    @Test
    void insert() {
        Genre genre = new Genre("test");
        long before = genreDaoJdbc.count();
        long id = genreDaoJdbc.insert(genre);
        long after = genreDaoJdbc.count();
        assertThat(before).isLessThan(after);
        assertThat(id).isEqualTo(4);
        Genre genreById = genreDaoJdbc.getById(id);
        assertSame(genre.getName(), genreById.getName());
    }

    @Test
    void update() {
        Genre genre = new Genre(2L, "testUp");
        genreDaoJdbc.update(genre);
        Genre byId2 = genreDaoJdbc.getById(2L);
        assertThat(byId2).isEqualTo(genre);
    }

    @Test
    void getById() {
        Genre byId2 = genreDaoJdbc.getById(1L);
        assertThat(byId2.getName()).isEqualTo("Roman");
    }

    @Test
    void getAll() {
        List<Genre> all = genreDaoJdbc.getAll();
        assertThat(all).contains(new Genre(1L, "Roman"));
        assertThat(all).contains(new Genre(2L, "Poem"));
        assertThat(all).contains(new Genre(3L, "A note about your boy"));
        assertThat(all.size()).isEqualTo(3);
    }

    @Test
    void deleteById() {
        long before = genreDaoJdbc.count();
        genreDaoJdbc.deleteById(3L);
        long after = genreDaoJdbc.count();
        assertThat(before).isGreaterThan(after);
        assertThatThrownBy(() -> genreDaoJdbc.getById(3L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}