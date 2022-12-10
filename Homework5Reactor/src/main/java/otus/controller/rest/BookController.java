package otus.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.controller.rest.dto.BookDto;
import otus.controller.rest.dto.BookDtoFromFront;
import otus.controller.rest.dto.CommentDto;
import otus.controller.rest.mapper.BookMapper;
import otus.controller.rest.mapper.CommentMapper;
import otus.dao.BookDao;
import otus.model.Book;
import otus.model.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookDao bookDao;
    private final BookMapper bookMapper;
    private final CommentMapper commentMapper;

    @PostMapping(value = "api/book")
    public Mono<BookDto> saveBook(@RequestBody BookDtoFromFront bookDto) {
        Book book = bookMapper.toModel(bookDto);
        return bookDao.save(book)
                .map(bookMapper::toDto);
    }

    @GetMapping(value = {"api/book"})
    public Flux<BookDto> allBooks() {
        return bookDao.findAll()
                .map(bookMapper::toDto);
    }

    @GetMapping(value = {"api/book/{id}"})
    public Mono<BookDto> getBook(@PathVariable("id") String id) {
        return bookDao.findById(id)
                .map(bookMapper::toDto);
    }

    @PutMapping("api/book")
    public Mono<BookDto> editBook(@RequestBody BookDtoFromFront bookDto) {
        Book book = bookMapper.toModel(bookDto);
        List<Comment> comments = bookDao.findById(book.getId())
                .map(Book::getComments).block();
        book.setComments(comments);
        return bookDao.save(book)
                .map(bookMapper::toDto);
    }

    @DeleteMapping("api/book/{id}")
    public HttpStatus deleteBook(@PathVariable("id") String id) {
        bookDao.deleteById(id).subscribe();
        return HttpStatus.OK;
    }

    @GetMapping("api/book/{id}/comments")
    public Mono<List<CommentDto>> commentBook(@PathVariable("id") String id) {
        return bookDao.findById(id)
                .map(Book::getComments)
                .map(comments -> comments
                        .stream()
                        .map(commentMapper::toDto)
                        .collect(Collectors.toList()));

    }
}
