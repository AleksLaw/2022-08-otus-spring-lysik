package otus.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import otus.model.Author;
import otus.model.Book;
import otus.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Import(BookDaoJpa.class)
class BookDaoJpaTest {

    @Autowired
    private BookDaoJpa bookDaoJpa;

    @Test
    void insert() {
        Book book = new Book("test",
                new Author("Karl", "Marks"),
                new Genre("Roman"));
        Book save = bookDaoJpa.save(book);
        Optional<Book> bookById = bookDaoJpa.getById(save.getId());
        Book actual = bookById.orElse(null);
        assertNotNull(actual);
        assertThat(save.getId()).isEqualTo(3);
        assertSame(save.getName(), actual.getName());
        assertSame(save.getAuthor(), actual.getAuthor());
        assertSame(save.getGenre(), actual.getGenre());
    }

    @Test
    void update() {
        Book book = new Book(2L, "test",
                new Author(1L, "Karl", "Marks"),
                new Genre(1L, "Roman"));
        bookDaoJpa.update(book);
        Optional<Book> bookById = bookDaoJpa.getById(2L);
        Book actual = bookById.orElse(null);
        assertNotNull(actual);
        assertThat(actual).isEqualTo(book);
    }

    @Test
    void getById() {
        Optional<Book> bookById = bookDaoJpa.getById(1L);
        Book actual = bookById.orElse(null);
        assertNotNull(actual);
        assertThat(actual.getName()).isEqualTo("Capital");
        assertThat(actual.getGenre()).isEqualTo(new Genre(1L, "Roman"));
        assertThat(actual.getAuthor()).isEqualTo(new Author(1L, "Karl", "Marks"));
    }

    @Test
    void getAll() {
        List<Book> all = bookDaoJpa.getAll();
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    void deleteById() {
        long before = bookDaoJpa.getAll().size();
        long l = bookDaoJpa.deleteById(2L);
        long after = bookDaoJpa.getAll().size();
        assertThat(before).isGreaterThan(after);
        assertEquals(Optional.empty(), bookDaoJpa.getById(3L));
        assertEquals(1L, l);
    }
}