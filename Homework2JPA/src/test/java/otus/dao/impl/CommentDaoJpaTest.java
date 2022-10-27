package otus.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import otus.model.Book;
import otus.model.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CommentDaoJpa.class)
class CommentDaoJpaTest {
    @Autowired
    private CommentDaoJpa commentDaoJpa;

    @Test
    void save() {
        Comment comment = new Comment("test", new Book(1L, "test", null, null));
        long before = commentDaoJpa.getAllByBookId(1L).size();
        Comment save = commentDaoJpa.save(comment);
        long after = commentDaoJpa.getAllByBookId(1L).size();
        assertThat(before).isLessThan(after);
        assertThat(save.getId()).isEqualTo(4);
        Optional<Comment> commentById = commentDaoJpa.getById(save.getId());
        Comment actual = commentById.orElse(null);
        assertNotNull(actual);
        assertSame(comment.getText(), actual.getText());
        assertSame(comment.getBook(), actual.getBook());
    }

    @Test
    void update() {
        Comment comment = new Comment(1L, "text", new Book(1L, "Capital", null, null));
        commentDaoJpa.update(comment);
        Optional<Comment> commentById = commentDaoJpa.getById(1L);
        Comment actual = commentById.orElse(null);
        assertNotNull(actual);
        assertThat(actual.getText()).isEqualTo(comment.getText());
        assertThat(actual.getBook().getName()).isEqualTo(comment.getBook().getName());
        assertThat(actual.getBook().getId()).isEqualTo(comment.getBook().getId());
    }

    @Test
    void getById() {
        Optional<Comment> commentById = commentDaoJpa.getById(1L);
        Comment actual = commentById.orElse(null);
        assertNotNull(actual);
        assertThat(actual.getText()).isEqualTo("so so book");
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getBook().getName()).isEqualTo("Capital");
    }

    @Test
    void getAllByBookId() {
        List<Comment> allByBookId = commentDaoJpa.getAllByBookId(1L);
        assertThat(allByBookId.size()).isEqualTo(3);
        assertThat(allByBookId.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void deleteById() {
        long before = commentDaoJpa.getAllByBookId(1L).size();
        long l = commentDaoJpa.deleteById(1L);
        long after = commentDaoJpa.getAllByBookId(1L).size();
        assertThat(before).isGreaterThan(after);
        assertEquals(1L, l);
    }
}