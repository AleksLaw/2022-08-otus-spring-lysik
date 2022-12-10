package otus.controller.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import otus.controller.rest.dto.AuthorDto;
import otus.controller.rest.dto.BookDto;
import otus.controller.rest.dto.CommentDto;
import otus.controller.rest.dto.GenreDto;
import otus.model.Comment;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CommentControllerTest {

    @Autowired
    private WebTestClient webTestClient;


    private CommentDto expectedDto;

    @BeforeEach
    public void setUp() {
        expectedDto = new CommentDto(null, "Karl", new BookDto("1", "test", new AuthorDto("1", "Karl", "Marks"), new GenreDto("1", "Roman")));
    }

    @Test
    void saveComment() {

        webTestClient.post()
                .uri("/api/comment")
                .body(Mono.just(expectedDto), Comment.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.text")
                .isEqualTo("Karl")
                .jsonPath("$.book.name")
                .isEqualTo("Capital")
                .jsonPath("$.book.author.name")
                .isEqualTo("Karl")
                .jsonPath("$.book.genre.name")
                .isEqualTo("Roman")
                .jsonPath("$.id")
                .isNotEmpty();
    }

    @Test
    void allComments() {

        webTestClient.get()
                .uri("/api/comment")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Comment.class);
    }

    @Test
    void updateComment() {
        webTestClient.put()
                .uri("/api/comment/1")
                .body(Mono.just(expectedDto), Comment.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.text")
                .isEqualTo("Karl")
                .jsonPath("$.book.name")
                .isEqualTo("Capital")
                .jsonPath("$.book.author.name")
                .isEqualTo("Karl")
                .jsonPath("$.book.genre.name")
                .isEqualTo("Roman")
                .jsonPath("$.id")
                .isEqualTo("1");
    }

    @Test
    void deleteComment() {
        webTestClient.delete()
                .uri("/api/comment/1")
                .exchange()
                .expectStatus()
                .isOk();
    }
}