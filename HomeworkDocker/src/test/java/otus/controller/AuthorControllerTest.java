package otus.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import otus.model.Author;
import otus.service.AuthorService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;
    private Author expected;

    @BeforeEach
    public void setUp() {
        expected = new Author(1L, "Karl", "Marks");
    }

    @Test
    void savePage() throws Exception {
        mvc.perform(get("/author/save"))
                .andExpect(status().isOk())
                .andExpect(view().name("author/authorSave"));
    }

    @Test
    void saveAuthor() throws Exception {
        mvc.perform(post("/author/save"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/author/all"));
    }

    @Test
    void allAuthorsPage() throws Exception {
        mvc.perform(get("/author/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("authors"))
                .andExpect(view().name("author/authorList"));
    }


    @Test
    void editPage() throws Exception {
        when(authorService.getAuthor(1L)).thenReturn(expected);
        mvc.perform(get("/author/edit")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("author/authorEdit"));
    }

    @Test
    void editAuthor() throws Exception {
        when(authorService.getAuthor(1L)).thenReturn(expected);
        mvc.perform(post("/author/edit")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/author/all"));
    }

    @Test
    void deletePage() throws Exception {
        when(authorService.getAuthor(1L)).thenReturn(expected);
        mvc.perform(get("/author/delete")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("author/authorDelete"));
    }

    @Test
    void deleteAuthor() throws Exception {
        mvc.perform(post("/author/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/author/all"));
    }
}