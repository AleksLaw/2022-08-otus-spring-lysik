package otus.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.controller.rest.dto.BookDto;
import otus.controller.rest.dto.BookDtoFromFront;
import otus.controller.rest.dto.CommentDto;
import otus.controller.rest.mapper.BookMapper;
import otus.controller.rest.mapper.CommentMapper;
import otus.model.Book;
import otus.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final CommentMapper commentMapper;

    @PostMapping(value = "api/book")
    public BookDto saveBook(@RequestBody BookDtoFromFront bookDto) {
        return bookMapper.toDto(
                bookService.saveBook(
                        bookMapper.toModel(bookDto)));
    }

    @GetMapping(value = {"api/book"})
    public List<BookDto> allBooks() {
        return bookService.getAllBook()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = {"api/book/{id}"})
    public BookDto getBook(@PathVariable("id") long id) {
        return bookMapper.toDto(bookService.getBook(id));
    }

    @PutMapping("api/book")
    public BookDto editBook(@RequestBody BookDtoFromFront bookDto) {
        Book book = bookMapper.toModel(bookDto);
        book.setComments(bookService.getBook(book.getId()).getComments());
        return bookMapper.toDto(bookService.saveBook(book));
    }

    @DeleteMapping("api/book/{id}")
    public HttpStatus deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(bookService.getBook(id));
        return HttpStatus.OK;
    }

    @GetMapping("api/book/{id}/comments")
    public List<CommentDto> commentBook(@PathVariable("id") long id) {
        return bookService.getBook(id).getComments()
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
}
