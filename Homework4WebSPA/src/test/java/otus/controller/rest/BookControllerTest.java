package otus.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import otus.controller.rest.dto.*;
import otus.controller.rest.mapper.BookMapper;
import otus.controller.rest.mapper.CommentMapper;
import otus.model.Author;
import otus.model.Book;
import otus.model.Comment;
import otus.model.Genre;
import otus.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookService bookService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookMapper bookMapper;
    @MockBean
    private CommentMapper commentMapper;
    private Book expected;
    private BookDto expectedDto;
    private BookDtoFromFront bookDtoFromFront;

    @BeforeEach
    public void setUp() {
        bookDtoFromFront = new BookDtoFromFront(null, "test", 1L, 1L);
        Author author = new Author(1L, "Karl", "Marks");
        Genre genre = new Genre(1L, "Roman");
        Book book = new Book(1L, "test", author, genre, List.of(new Comment(1L, "asda", expected)));
        expected = new Book(1L, "test", author, genre, List.of(new Comment(1L, "asda", book)));
        expectedDto = new BookDto(1L, "test", new AuthorDto(1L, "Karl", "Marks"), new GenreDto(1L, "Roman"));
    }

    @Test
    void saveBook() throws Exception {
        when(bookService.saveBook(expected)).thenReturn(expected);
        when(bookMapper.toModel(bookDtoFromFront)).thenReturn(expected);
        when(bookMapper.toDto(expected)).thenReturn(expectedDto);
        expected.setComments(null);
        mvc.perform(post("/api/book")
                        .content(objectMapper.writeValueAsString(bookDtoFromFront))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void allBook() throws Exception {
        when(bookService.getAllBook()).thenReturn(List.of(expected));
        when(bookMapper.toModel(bookDtoFromFront)).thenReturn(expected);
        when(bookMapper.toDto(expected)).thenReturn(expectedDto);
        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        List.of(expectedDto))));
    }

    @Test
    void updateBook() throws Exception {
        when(bookService.getBook(1L)).thenReturn(expected);
        when(bookService.saveBook(expected)).thenReturn(expected);
        when(bookMapper.toModel(bookDtoFromFront)).thenReturn(expected);
        when(bookMapper.toDto(expected)).thenReturn(expectedDto);
        bookDtoFromFront.setId(1L);
        mvc.perform(put("/api/book")
                        .content(objectMapper.writeValueAsString(bookDtoFromFront))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString((expectedDto))));
    }

    @Test
    void deleteBook() throws Exception {
        when(bookService.getBook(1L)).thenReturn(expected);
        doNothing().when(bookService).deleteBook(expected);
        mvc.perform(delete("/api/book/1")
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBookById() throws Exception {
        when(bookService.getBook(1L)).thenReturn(expected);
        when(bookMapper.toModel(bookDtoFromFront)).thenReturn(expected);
        when(bookMapper.toDto(expected)).thenReturn(expectedDto);
        mvc.perform(get("/api/book/1")
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void getCommentsBookById() throws Exception {
        when(bookService.getBook(1L)).thenReturn(expected);
        List<CommentDto> collect = expected.getComments()
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
        mvc.perform(get("/api/book/1/comments")
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(collect)));
    }
}