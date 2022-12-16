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
import otus.service.AuthorService;

import static org.mockito.Mockito.when;
import static org.springframework.security.core.userdetails.User.builder;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AuthorService authorService;
    private Author expected;
    UserDetails user = builder()
            .username("a")
            .password("a")
            .roles("USER")
            .build();

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        expected = new Author(1L, "Karl", "Marks");
    }

    public static RequestPostProcessor userHttpBasic(UserDetails user) {
        return SecurityMockMvcRequestPostProcessors.user(user);
    }

    @Test
    void savePage() throws Exception {
        mvc.perform(get("/author/save")
                        .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andExpect(view().name("author/authorSave"));
    }

    @Test
    void editPage() throws Exception {
        when(authorService.getAuthor(1L)).thenReturn(expected);
        mvc.perform(get("/author/edit")
                        .with(userHttpBasic(user))
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("author/authorEdit"));
    }

    @Test
    void deletePage() throws Exception {
        when(authorService.getAuthor(1L)).thenReturn(expected);
        mvc.perform(get("/author/delete")
                        .with(userHttpBasic(user))
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("author/authorDelete"));
    }

}