package otus.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import otus.controller.rest.dto.AuthorDto;
import otus.controller.rest.mapper.AuthorMapper;
import otus.model.Author;
import otus.service.AuthorService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthorMapper authorMapper;
    private Author expected;
    private AuthorDto expectedDto;


    @BeforeEach
    public void setUp() {
        expected = new Author("Karl", "Marks");
        expectedDto = new AuthorDto(null, "Karl", "Marks");
    }

    @Test
    void saveAuthor() throws Exception {
        when(authorService.saveAuthor(expected)).thenReturn(expected);
        when(authorMapper.toModel(expectedDto)).thenReturn(expected);
        when(authorMapper.toDto(expected)).thenReturn(expectedDto);
        mvc.perform(post("/api/author")
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void allAuthors() throws Exception {
        when(authorService.getAllAuthor()).thenReturn(List.of(expected));
        when(authorMapper.toModel(expectedDto)).thenReturn(expected);
        when(authorMapper.toDto(expected)).thenReturn(expectedDto);
        mvc.perform(get("/api/author"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        List.of(expectedDto))));
    }

    @Test
    void updateAuthor() throws Exception {
        expected.setId(1L);
        when(authorService.saveAuthor(expected)).thenReturn(expected);
        when(authorMapper.toModel(expectedDto)).thenReturn(expected);
        when(authorMapper.toDto(expected)).thenReturn(expectedDto);
        expectedDto.setId(1L);
        mvc.perform(put("/api/author/1")
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void deleteAuthor() throws Exception {
        expected.setId(1L);
        when(authorService.getAuthor(1L)).thenReturn(expected);
        doNothing().when(authorService).deleteAuthor(expected);
        mvc.perform(delete("/api/author/1")
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}