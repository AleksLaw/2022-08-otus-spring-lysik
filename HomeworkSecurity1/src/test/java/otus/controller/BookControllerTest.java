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
import otus.service.AuthorService;
import otus.service.BookService;
import otus.service.GenreService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.core.userdetails.User.builder;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthorService authorService;
    @Autowired
    private WebApplicationContext context;
    private Book expected;
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
        expected = new Book(1L, "test", author, genre, List.of(new Comment("asda", expected)));
    }

    public static RequestPostProcessor userHttpBasic(UserDetails user) {
        return SecurityMockMvcRequestPostProcessors.user(user);
    }

    @Test
    void savePage() throws Exception {
        mvc.perform(get("/book/save")
                        .with(userHttpBasic(user)))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookSave"));
    }

    @Test
    void allBooksPage() throws Exception {
        mvc.perform(get("/book/all")
                        .with(userHttpBasic(user)))
                .andExpect(model().attributeExists("books"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookList"));
    }

    @Test
    void editPage() throws Exception {
        when(bookService.getBook(1L)).thenReturn(expected);
        mvc.perform(get("/book/edit")
                        .with(userHttpBasic(user))
                        .param("id", "1"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attributeExists("book"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookEdit"));
    }

    @Test
    void deletePage() throws Exception {
        when(bookService.getBook(1L)).thenReturn(expected);
        mvc.perform(get("/book/delete")
                        .with(userHttpBasic(user))
                        .param("id", "1"))
                .andExpect(model().attributeExists("book"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookDelete"));
    }

    @Test
    void commentPage() throws Exception {
        when(bookService.getBook(1L)).thenReturn(expected);
        mvc.perform(get("/book/comments")
                        .with(userHttpBasic(user))
                        .param("id", "1"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookComment"));
    }
}