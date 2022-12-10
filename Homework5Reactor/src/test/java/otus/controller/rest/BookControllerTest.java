package otus.controller.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import otus.controller.rest.dto.AuthorDto;
import otus.controller.rest.dto.BookDto;
import otus.controller.rest.dto.BookDtoFromFront;
import otus.controller.rest.dto.GenreDto;
import otus.model.Author;
import otus.model.Book;
import otus.model.Comment;
import otus.model.Genre;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private Book expected;
    private BookDto expectedDto;
    private BookDtoFromFront bookDtoFromFront;

    @BeforeEach
    public void setUp() {
        bookDtoFromFront = new BookDtoFromFront(null, "test", "1", "1");
        Author author = new Author("1", "Karl", "Marks");
        Genre genre = new Genre("1", "Roman");
        Book book = new Book("1", "test", author, genre, List.of(new Comment("1", "asda", expected)));
        expected = new Book("1", "test", author, genre, List.of(new Comment("1", "asda", book)));
        expectedDto = new BookDto("1", "test", new AuthorDto("1", "Karl", "Marks"), new GenreDto("1", "Roman"));
    }

    @Test
    void saveBook() {

        webTestClient.post()
                .uri("/api/book")
                .body(Mono.just(bookDtoFromFront), Book.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name")
                .isEqualTo("test")
                .jsonPath("$.author.name")
                .isEqualTo("Karl")
                .jsonPath("$.author.surname")
                .isEqualTo("Marks")
                .jsonPath("$.author.id")
                .isEqualTo("1")
                .jsonPath("$.genre.name")
                .isEqualTo("Roman")
                .jsonPath("$.genre.id")
                .isEqualTo("1")
                .jsonPath("$.id")
                .isNotEmpty();
    }

    @Test
    void allBook() {
        webTestClient.get()
                .uri("/api/book")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Book.class);
    }


    @Test
    void deleteBook() {
        webTestClient.delete()
                .uri("/api/book/1")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void getBookById() {
        webTestClient.get()
                .uri("/api/book/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name")
                .isEqualTo("Capital")
                .jsonPath("$.author.name")
                .isEqualTo("Karl")
                .jsonPath("$.author.surname")
                .isEqualTo("Marks")
                .jsonPath("$.author.id")
                .isEqualTo("1")
                .jsonPath("$.genre.name")
                .isEqualTo("Roman")
                .jsonPath("$.genre.id")
                .isEqualTo("1")
                .jsonPath("$.id")
                .isNotEmpty();
    }

}