package otus.service.domenservice.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.dao.CommentDao;
import otus.model.Author;
import otus.model.Book;
import otus.model.Comment;
import otus.model.Genre;
import otus.service.domenservice.BookService;
import otus.service.domenservice.CommentService;
import otus.service.ioservice.IOService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommentServiceTest {
    @MockBean
    private IOService ioService;
    @MockBean
    private CommentDao commentDAO;
    @MockBean
    private BookService bookService;
    @Autowired
    private CommentService commentService;
    private Comment expected;
    private Book expectedBook;

    @BeforeEach
    public void setUp() {
        expectedBook = new Book(1L, "test",
                new Author(1L, "1", "1"),
                new Genre(1L, "1"));
        expected = new Comment("test", expectedBook);
    }

    @Test
    void saveComment() {
        when(ioService.getString()).thenReturn("test");
        when(bookService.getBook()).thenReturn(expectedBook);
        when(commentDAO.save(expected)).thenReturn(expected);
        Comment actual = commentService.saveComment();
        assertEquals(expected, actual);
    }

    @Test
    void getCommentById() {
        expected.setId(1L);
        when(ioService.getLong()).thenReturn(1L);
        when(commentDAO.getById(1L)).thenReturn(Optional.of(expected));
        Comment actual = commentService.getCommentById();
        assertEquals(expected, actual);
    }

    @Test
    void getAllCommentByBookId() {
        expected.setId(1L);
        when(ioService.getLong()).thenReturn(1L);
        when(commentDAO.getAllByBookId(1L)).thenReturn(List.of(expected));
        when(commentDAO.getById(1L)).thenReturn(Optional.of(expected));
        List<Comment> actual = commentService.getAllCommentByBookId();
        assertEquals(1, actual.size());
        assertEquals(expected, actual.get(0));
    }

    @Test
    void updateComment() {
        when(ioService.getString()).thenReturn("test");
        when(ioService.getLong()).thenReturn(1L);
        when(commentDAO.getById(1L)).thenReturn(Optional.of(expected));
        expected.setId(1L);
        when(bookService.getBook()).thenReturn(expectedBook);
        when(commentDAO.update(expected)).thenReturn(expected);
        Comment comment = commentService.updateComment();
        assertEquals(expected, comment);
    }

    @Test
    void deleteComment() {
        when(ioService.getLong()).thenReturn(1L);
        when(commentDAO.deleteById(1L)).thenReturn(1L);
        expected.setId(1L);
        when(commentDAO.getById(1L)).thenReturn(Optional.of(expected));
        long actual = commentService.deleteComment();
        assertEquals(1, actual);
    }
}