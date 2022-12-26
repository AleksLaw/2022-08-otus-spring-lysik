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
import otus.model.Genre;
import otus.service.GenreService;

import static org.mockito.Mockito.when;
import static org.springframework.security.core.userdetails.User.builder;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private GenreService genreService;
    private Genre expected;
    UserDetails user = builder()
            .username("a")
            .password("a")
            .roles("USER")
            .build();

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        expected = new Genre(1L, "Karl");
    }

    public static RequestPostProcessor userHttpBasic(UserDetails user) {
        return SecurityMockMvcRequestPostProcessors.user(user);
    }

    @Test
    void savePage() throws Exception {
        mvc.perform(get("/genre/save")
                        .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andExpect(view().name("genre/genreSave"));
    }

    @Test
    void allGenresPage() throws Exception {
        mvc.perform(get("/genre/all")
                        .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("genres"))
                .andExpect(view().name("genre/genreList"));
    }


    @Test
    void editPage() throws Exception {
        when(genreService.getGenre(1L)).thenReturn(expected);
        mvc.perform(get("/genre/edit")
                        .with(userHttpBasic(user))
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("genre"))
                .andExpect(view().name("genre/genreEdit"));
    }

    @Test
    void deletePage() throws Exception {
        when(genreService.getGenre(1L)).thenReturn(expected);
        mvc.perform(get("/genre/delete")
                        .with(userHttpBasic(user))
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("genre"))
                .andExpect(view().name("genre/genreDelete"));
    }

}