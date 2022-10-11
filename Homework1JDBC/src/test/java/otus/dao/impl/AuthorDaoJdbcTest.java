package otus.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import otus.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertSame;


@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void count() {
        long count = authorDaoJdbc.count();
        authorDaoJdbc.getAll().forEach(System.out::println);
        assertThat(count).isEqualTo(3);
    }

    @Test
    void insert() {
        Author author = new Author("test", "test");
        long before = authorDaoJdbc.count();
        long id = authorDaoJdbc.insert(author);
        long after = authorDaoJdbc.count();
        assertThat(before).isLessThan(after);
        assertThat(id).isEqualTo(4);
        Author authorById = authorDaoJdbc.getById(id);
        assertSame(author.getName(), authorById.getName());
        assertSame(author.getSurname(), authorById.getSurname());
    }

    @Test
    void update() {
        Author author = new Author(2L, "test", "test");
        authorDaoJdbc.update(author);
        Author authorById = authorDaoJdbc.getById(2L);
        assertThat(authorById).isEqualTo(author);
    }

    @Test
    void getById() {
        Author authorById = authorDaoJdbc.getById(1L);
        assertThat(authorById.getName()).isEqualTo("Karl");
        assertThat(authorById.getSurname()).isEqualTo("Marks");
    }

    @Test
    void getAll() {
        List<Author> all = authorDaoJdbc.getAll();
        assertThat(all).contains(new Author(1L, "Karl", "Marks"));
        assertThat(all).contains(new Author(2L, "Uzik", "Stalin"));
        assertThat(all).contains(new Author(3L, "Vova", "Lenin"));
        assertThat(all.size()).isEqualTo(3);
    }

    @Test
    void deleteById() {
        long before = authorDaoJdbc.count();
        authorDaoJdbc.deleteById(3L);
        long after = authorDaoJdbc.count();
        assertThat(before).isGreaterThan(after);
        assertThatThrownBy(() -> authorDaoJdbc.getById(3L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}