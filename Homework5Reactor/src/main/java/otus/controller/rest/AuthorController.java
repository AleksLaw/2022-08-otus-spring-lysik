package otus.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.controller.rest.dto.AuthorDto;
import otus.controller.rest.mapper.AuthorMapper;
import otus.dao.AuthorDao;
import otus.model.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorDao authorDao;
    private final AuthorMapper authorMapper;

    @PostMapping("api/author")
    public Mono<AuthorDto> saveAuthor(@RequestBody AuthorDto authorDto) {
        return authorDao.save(authorMapper.toModel(authorDto))
                .map(authorMapper::toDto);
    }

    @GetMapping(value = {"api/author"})
    public Flux<AuthorDto> allAuthors() {
        return authorDao.findAll()
                .map(authorMapper::toDto);
    }

    @PutMapping("api/author/{id}")
    public Mono<AuthorDto> editAuthor(@PathVariable("id") String id, @RequestBody AuthorDto authorDto) {
        authorDto.setId(id);
        Author author = authorMapper.toModel(authorDto);
        return authorDao.save(author)
                .map(authorMapper::toDto);
    }

    @DeleteMapping("api/author/{id}")
    public HttpStatus deleteAuthor(@PathVariable("id") String id) {
        authorDao.deleteById(id).subscribe();
        return HttpStatus.OK;
    }
}
