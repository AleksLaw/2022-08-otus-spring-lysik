package otus.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import otus.model.Author;
import otus.model.Book;
import otus.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertSame;


@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;


    @Test
    void count() {
        long count = bookDaoJdbc.count();
        bookDaoJdbc.getAll().forEach(System.out::println);
        assertThat(count).isEqualTo(2);
    }

    @Test
    void insert() {
        Book book = new Book("test",
                new Author("1", "1"),
                new Genre("2"));
        long before = bookDaoJdbc.count();
        long id = bookDaoJdbc.insert(book);
        long after = bookDaoJdbc.count();
        assertThat(before).isLessThan(after);
        assertThat(id).isEqualTo(3);
        Book bookById = bookDaoJdbc.getById(id);
        assertSame(book.getName(), bookById.getName());
    }

    @Test
    void update() {
        Book book = new Book(2L, "test",
                new Author(1L, "Karl", "Marks"),
                new Genre(1L, "Roman"));
        bookDaoJdbc.update(book);
        Book bookById = bookDaoJdbc.getById(2L);
        assertThat(bookById).isEqualTo(book);
    }

    @Test
    void getById() {
        Book bookById = bookDaoJdbc.getById(1L);
        assertThat(bookById.getName()).isEqualTo("Capital");
        assertThat(bookById.getGenre()).isEqualTo(new Genre(1L, "Roman"));
        assertThat(bookById.getAuthor()).isEqualTo(new Author(1L, "Karl", "Marks"));
    }

    @Test
    void getAll() {
        List<Book> all = bookDaoJdbc.getAll();
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    void deleteById() {
        long before = bookDaoJdbc.count();
        bookDaoJdbc.deleteById(2L);
        long after = bookDaoJdbc.count();
        assertThat(before).isGreaterThan(after);
        assertThatThrownBy(() -> bookDaoJdbc.getById(2L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}