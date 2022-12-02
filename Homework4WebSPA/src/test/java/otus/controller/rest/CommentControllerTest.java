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
import otus.controller.rest.dto.BookDto;
import otus.controller.rest.dto.CommentDto;
import otus.controller.rest.dto.GenreDto;
import otus.controller.rest.mapper.CommentMapper;
import otus.model.Author;
import otus.model.Book;
import otus.model.Comment;
import otus.model.Genre;
import otus.service.CommentService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CommentMapper commentMapper;
    @MockBean
    private CommentService commentService;
    private Comment expected;
    private CommentDto expectedDto;

    @BeforeEach
    public void setUp() {
        Author author = new Author(1L, "Karl", "Marks");
        Genre genre = new Genre(1L, "Roman");
        Book book = new Book(1L, "test", author, genre, null);
        expected = new Comment(1L, "Karl", book);
        expectedDto = new CommentDto(null, "Karl", new BookDto(1l, "test", new AuthorDto(1L, "Karl", "Marks"), new GenreDto(1L, "Roman")));
    }

    @Test
    void saveComment() throws Exception {
        when(commentService.saveComment(expected)).thenReturn(expected);
        when(commentMapper.toModel(expectedDto)).thenReturn(expected);
        when(commentMapper.toDto(expected)).thenReturn(expectedDto);
        mvc.perform(post("/api/comment")
                        .content(objectMapper.writeValueAsString(expectedDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void allComments() throws Exception {
        when(commentService.getAllComment()).thenReturn(List.of(expected));
        when(commentMapper.toModel(expectedDto)).thenReturn(expected);
        when(commentMapper.toDto(expected)).thenReturn(expectedDto);
        mvc.perform(get("/api/comment"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        List.of(expectedDto))));
    }

    @Test
    void updateComment() throws Exception {
        expected.setId(1L);
        when(commentService.saveComment(expected)).thenReturn(expected);
        when(commentMapper.toModel(expectedDto)).thenReturn(expected);
        when(commentMapper.toDto(expected)).thenReturn(expectedDto);
        expectedDto.setId(1L);
        mvc.perform(put("/api/comment/1")
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void deleteComment() throws Exception {
        expected.setId(1L);
        when(commentService.getComment(1L)).thenReturn(expected);
        doNothing().when(commentService).deleteComment(expected);
        mvc.perform(delete("/api/comment/1")
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}