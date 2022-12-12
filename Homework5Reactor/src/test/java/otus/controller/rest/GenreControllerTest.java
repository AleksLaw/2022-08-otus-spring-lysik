package otus.controller.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import otus.controller.rest.dto.GenreDto;
import otus.model.Genre;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GenreControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private Genre expected;
    private GenreDto expectedDto;

    @BeforeEach
    public void setUp() {
        expected = new Genre("1", "Karl");
        expectedDto = new GenreDto(null, "Karl");
    }

    @Test
    void saveGenre() {

        webTestClient.post()
                .uri("/api/genre")
                .body(Mono.just(expectedDto), Genre.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name")
                .isEqualTo("Karl")
                .jsonPath("$.id")
                .isNotEmpty();
    }

    @Test
    void allGenres() {

        webTestClient.get()
                .uri("/api/genre")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Genre.class);
    }

    @Test
    void updateGenre() {
        webTestClient.put()
                .uri("/api/genre/1")
                .body(Mono.just(expected), Genre.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name")
                .isEqualTo("Karl")
                .jsonPath("$.id")
                .isEqualTo(1);
    }

    @Test
    void deleteGenre() {
        webTestClient.delete()
                .uri("/api/genre/1")
                .exchange()
                .expectStatus()
                .isOk();
    }
}