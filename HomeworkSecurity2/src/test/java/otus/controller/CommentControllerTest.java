package otus.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import otus.model.Author;
import otus.model.Book;
import otus.model.Comment;
import otus.model.Genre;
import otus.service.BookService;
import otus.service.CommentService;

import static org.mockito.Mockito.when;
import static org.springframework.security.core.userdetails.User.builder;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;
    private Comment expected;
    UserDetails user = builder()
            .username("a")
            .password("a")
            .roles("USER")
            .build();

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        Author author = new Author(1L, "Karl", "Marks");
        Genre genre = new Genre(1L, "Roman");
        Book book = new Book(1L, "test", author, genre, null);
        expected = new Comment(1L, "Karl", book);
    }

    public static RequestPostProcessor userHttpBasic(UserDetails user) {
        return SecurityMockMvcRequestPostProcessors.user(user);
    }

    @Test
    void savePage() throws Exception {
        mvc.perform(get("/comment/save")
                        .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/commentSave"));
    }

    @Test
    void allCommentsPage() throws Exception {
        mvc.perform(get("/comment/all")
                        .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("comments"))
                .andExpect(view().name("comment/commentList"));
    }


    @Test
    void editPage() throws Exception {
        when(commentService.getComment(1L)).thenReturn(expected);
        mvc.perform(get("/comment/edit")
                        .with(userHttpBasic(user))
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("comment"))
                .andExpect(view().name("comment/commentEdit"));
    }

    @Test
    void deletePage() throws Exception {
        when(commentService.getComment(1L)).thenReturn(expected);
        mvc.perform(get("/comment/delete")
                        .with(userHttpBasic(user))
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("comment"))
                .andExpect(view().name("comment/commentDelete"));
    }
}