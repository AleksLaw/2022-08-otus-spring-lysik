package otus.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import otus.model.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {

    @Autowired
    private AuthorDaoJpa authorDaoJpa;


    @Test
    void insert() {
        Author author = new Author("test", "test");
        long before = authorDaoJpa.getAll().size();
        Author save = authorDaoJpa.save(author);
        long after = authorDaoJpa.getAll().size();
        assertThat(before).isLessThan(after);
        assertThat(save.getId()).isEqualTo(4);
        Optional<Author> authorById = authorDaoJpa.getById(save.getId());
        Author actual = authorById.orElse(null);
        assertNotNull(actual);
        assertSame(author.getName(), actual.getName());
        assertSame(author.getSurname(), actual.getSurname());
    }

    @Test
    void update() {
        Author author = new Author(2L, "test", "test");
        authorDaoJpa.update(author);
        Optional<Author> authorById = authorDaoJpa.getById(2L);
        Author actual = authorById.orElse(null);
        assertNotNull(actual);
        assertThat(actual).isEqualTo(author);
    }

    @Test
    void getById() {
        Optional<Author> authorById = authorDaoJpa.getById(1L);
        Author actual = authorById.orElse(null);
        assertNotNull(actual);
        assertThat(actual.getName()).isEqualTo("Karl");
        assertThat(actual.getSurname()).isEqualTo("Marks");
    }

    @Test
    void getAll() {
        List<Author> all = authorDaoJpa.getAll();
        assertThat(all.size()).isEqualTo(3);
    }

    @Test
    void deleteById() {
        long before = authorDaoJpa.getAll().size();
        authorDaoJpa.deleteById(3L);
        long after = authorDaoJpa.getAll().size();
        assertThat(before).isGreaterThan(after);
        Optional<Author> actual = authorDaoJpa.getById(3L);
        assertEquals(Optional.empty(), actual);
    }
}