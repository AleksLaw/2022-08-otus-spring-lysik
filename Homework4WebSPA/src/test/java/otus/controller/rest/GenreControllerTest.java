package otus.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import otus.controller.rest.dto.GenreDto;
import otus.controller.rest.mapper.GenreMapper;
import otus.model.Genre;
import otus.service.GenreService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GenreMapper genreMapper;
    @MockBean
    private GenreService genreService;
    private Genre expected;
    private GenreDto expectedDto;

    @BeforeEach
    public void setUp() {
        expected = new Genre(1L, "Karl");
        expectedDto = new GenreDto(null, "Karl");
    }

    @Test
    void saveAuthor() throws Exception {
        when(genreService.saveGenre(expected)).thenReturn(expected);
        when(genreMapper.toModel(expectedDto)).thenReturn(expected);
        when(genreMapper.toDto(expected)).thenReturn(expectedDto);
        mvc.perform(post("/api/genre")
                        .content(objectMapper.writeValueAsString(expectedDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void allAuthors() throws Exception {
        when(genreService.getAllGenre()).thenReturn(List.of(expected));
        when(genreMapper.toModel(expectedDto)).thenReturn(expected);
        when(genreMapper.toDto(expected)).thenReturn(expectedDto);
        mvc.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        List.of(expectedDto))));
    }

    @Test
    void updateAuthor() throws Exception {
        expected.setId(1L);
        when(genreService.saveGenre(expected)).thenReturn(expected);
        when(genreMapper.toModel(expectedDto)).thenReturn(expected);
        when(genreMapper.toDto(expected)).thenReturn(expectedDto);
        expectedDto.setId(1L);
        mvc.perform(put("/api/genre/1")
                        .content(objectMapper.writeValueAsString(expectedDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void deleteAuthor() throws Exception {
        expected.setId(1L);
        when(genreService.getGenre(1L)).thenReturn(expected);
        doNothing().when(genreService).deleteGenre(expected);
        mvc.perform(delete("/api/genre/1")
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}