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
import otus.service.AuthorService;
import otus.service.BookService;
import otus.service.GenreService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private Book expected;

    @BeforeEach
    public void setUp() {
        Author author = new Author(1L, "Karl", "Marks");
        Genre genre = new Genre(1L, "Roman");
        expected = new Book(1L, "test", author, genre, List.of(new Comment("asda", expected)));
    }

    @Test
    void savePage() throws Exception {
        mvc.perform(get("/book/save"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookSave"));
    }

    @Test
    void saveBook() throws Exception {
        mvc.perform(post("/book/save"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/all"));
    }

    @Test
    void allBooksPage() throws Exception {
        mvc.perform(get("/book/all"))
                .andExpect(model().attributeExists("books"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookList"));
    }

    @Test
    void editPage() throws Exception {
        when(bookService.getBook(1L)).thenReturn(expected);
        mvc.perform(get("/book/edit")
                        .param("id", "1"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attributeExists("book"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookEdit"));
    }

    @Test
    void editBook() throws Exception {
        when(bookService.getBook(1L)).thenReturn(expected);
        mvc.perform(post("/book/edit")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/all"));
    }

    @Test
    void deletePage() throws Exception {
        when(bookService.getBook(1L)).thenReturn(expected);
        mvc.perform(get("/book/delete")
                        .param("id", "1"))
                .andExpect(model().attributeExists("book"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookDelete"));
    }

    @Test
    void deleteBook() throws Exception {
        when(bookService.getBook(1L)).thenReturn(expected);
        mvc.perform(post("/book/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/all"));
    }

    @Test
    void commentPage() throws Exception {
        when(bookService.getBook(1L)).thenReturn(expected);
        mvc.perform(get("/book/comments")
                        .param("id", "1"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookComment"));
    }
}