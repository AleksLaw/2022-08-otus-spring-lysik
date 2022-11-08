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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
                new Genre(1L, "1"),
                null);
        expected = new Comment("test", expectedBook);
    }

    @Test
    void saveComment() {
        when(ioService.getString()).thenReturn("test");
        when(bookService.getBook(1L)).thenReturn(expectedBook);
        when(commentDAO.save(expected)).thenReturn(expected);
        Comment actual = commentService.saveComment("test", expectedBook);
        assertEquals(expected, actual);
    }

    @Test
    void getCommentById() {
        expected.setId(1L);
        when(ioService.getLong()).thenReturn(1L);
        when(commentDAO.findById(1L)).thenReturn(Optional.of(expected));
        Comment actual = commentService.getComment(1L);
        assertEquals(expected, actual);
    }

    @Test
    void getAllComment() {
        when(commentDAO.findAll()).thenReturn(Collections.singletonList(expected));
        List<Comment> allComment = commentService.getAllComment();
        assertEquals(1, allComment.size());
        assertEquals(expected, allComment.get(0));
    }

    @Test
    void updateComment() {
        when(ioService.getString()).thenReturn("test");
        when(ioService.getLong()).thenReturn(1L);
        when(commentDAO.findById(1L)).thenReturn(Optional.of(expected));
        expected.setId(1L);
        when(bookService.getBook(1L)).thenReturn(expectedBook);
        when(commentDAO.save(expected)).thenReturn(expected);
        Comment comment = commentService.updateComment(expected);
        assertEquals(expected, comment);
    }

    @Test
    void deleteComment() {
        when(ioService.getLong()).thenReturn(1L);
        expected.setId(1L);
        when(commentDAO.findById(1L)).thenReturn(Optional.of(expected));
        commentService.deleteComment(expected);
        verify(commentDAO, times(1)).deleteById(1L);
    }
}