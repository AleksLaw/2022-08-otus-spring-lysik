package otus.controller.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import otus.controller.rest.dto.AuthorDto;
import otus.model.Author;
import reactor.core.publisher.Mono;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthorControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private Author expected;
    private AuthorDto expectedDto;


    @BeforeEach
    public void setUp() {
        expected = new Author("Karl", "Marks");
        expectedDto = new AuthorDto(null, "Karl", "Marks");
    }

    @Test
    void saveAuthor() {

        webTestClient.post()
                .uri("/api/author")
                .body(Mono.just(expectedDto), Author.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name")
                .isEqualTo("Karl")
                .jsonPath("$.surname")
                .isEqualTo("Marks")
                .jsonPath("$.id")
                .isNotEmpty();
    }

    @Test
    void allAuthors() {

        webTestClient.get()
                .uri("/api/author")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Author.class);
    }

    @Test
    void updateAuthor() {
        webTestClient.put()
                .uri("/api/author/1")
                .body(Mono.just(expected), Author.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name")
                .isEqualTo("Karl")
                .jsonPath("$.surname")
                .isEqualTo("Marks")
                .jsonPath("$.id")
                .isEqualTo(1);
    }

    @Test
    void deleteAuthor() {
        webTestClient.delete()
                .uri("/api/author/1")
                .exchange()
                .expectStatus()
                .isOk();
    }
}