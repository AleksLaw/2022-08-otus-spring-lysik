package otus.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import otus.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(count).isEqualTo(2);
    }

    @Test
    void insert() {
        Genre genre = new Genre(13L, "test");
        long insert = genreDaoJdbc.insert(genre);
        Genre byId = genreDaoJdbc.getById(insert);
        assertSame(genre, byId);
    }

    @Test
    void update() {
    }

    @Test
    void getById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void deleteById() {
    }
}