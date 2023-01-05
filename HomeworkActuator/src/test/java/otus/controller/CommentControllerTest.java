package otus.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import otus.model.Author;
import otus.model.Book;
import otus.model.Comment;
import otus.model.Genre;
import otus.service.BookService;
import otus.service.CommentService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;
    private Comment expected;

    @BeforeEach
    public void setUp() {
        Author author = new Author(1L, "Karl", "Marks");
        Genre genre = new Genre(1L, "Roman");
        Book book = new Book(1L, "test", author, genre, null);
        expected = new Comment(1L, "Karl", book);
    }

    @Test
    void savePage() throws Exception {
        mvc.perform(get("/comment/save"))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/commentSave"));
    }

    @Test
    void saveComment() throws Exception {
        mvc.perform(post("/comment/save"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comment/all"));
    }

    @Test
    void allCommentsPage() throws Exception {
        mvc.perform(get("/comment/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("comments"))
                .andExpect(view().name("comment/commentList"));
    }


    @Test
    void editPage() throws Exception {
        when(commentService.getComment(1L)).thenReturn(expected);
        mvc.perform(get("/comment/edit")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("comment"))
                .andExpect(view().name("comment/commentEdit"));
    }

    @Test
    void editComment() throws Exception {
        when(commentService.getComment(1L)).thenReturn(expected);
        mvc.perform(post("/comment/edit")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comment/all"));
    }

    @Test
    void deletePage() throws Exception {
        when(commentService.getComment(1L)).thenReturn(expected);
        mvc.perform(get("/comment/delete")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("comment"))
                .andExpect(view().name("comment/commentDelete"));
    }

    @Test
    void deleteComment() throws Exception {
        mvc.perform(post("/comment/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comment/all"));
    }
}