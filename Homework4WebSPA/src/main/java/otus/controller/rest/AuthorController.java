package otus.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.controller.rest.dto.AuthorDto;
import otus.controller.rest.mapper.AuthorMapper;
import otus.model.Author;
import otus.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @PostMapping("api/author")
    public AuthorDto saveAuthor(@RequestBody AuthorDto authorDto) {
        return authorMapper.toDto(
                authorService.saveAuthor(
                        authorMapper.toModel(authorDto)));
    }

    @GetMapping(value = {"api/author"})
    public List<AuthorDto> allAuthors() {
        return authorService.getAllAuthor()
                .stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("api/author/{id}")
    public Author editAuthor(@PathVariable("id") long id, @RequestBody AuthorDto authorDto) {
        Author author = authorMapper.toModel(authorDto);
        author.setId(id);
        return authorService.saveAuthor(author);
    }

    @DeleteMapping("api/author/{id}")
    public HttpStatus deleteAuthor(@PathVariable("id") long id) {
        authorService.deleteAuthor(authorService.getAuthor(id));
        return HttpStatus.OK;
    }
}
