package otus.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.controller.rest.dto.GenreDto;
import otus.controller.rest.mapper.GenreMapper;
import otus.dao.GenreDao;
import otus.model.Genre;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreDao genreDao;
    private final GenreMapper genreMapper;

    @PostMapping("api/genre")
    public Mono<GenreDto> saveGenre(@RequestBody GenreDto genreDto) {
        return genreDao.save(genreMapper.toModel(genreDto))
                .map(genreMapper::toDto);
    }

    @GetMapping(value = {"api/genre"})
    public Flux<GenreDto> allGenres() {
        return genreDao.findAll()
                .map(genreMapper::toDto);
    }

    @PutMapping("api/genre/{id}")
    public Mono<GenreDto> editGenre(@PathVariable("id") String id, @RequestBody GenreDto genreDto) {
        Genre genre = genreMapper.toModel(genreDto);
        genre.setId(id);
        return genreDao.save(genre)
                .map(genreMapper::toDto);
    }

    @DeleteMapping("api/genre/{id}")
    public HttpStatus deleteGenre(@PathVariable("id") String id) {
        genreDao.delete(Objects.requireNonNull(genreDao.findById(id).block())).block();
        return HttpStatus.OK;
    }
}
