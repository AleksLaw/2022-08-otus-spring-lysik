package otus.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.controller.rest.dto.GenreDto;
import otus.controller.rest.mapper.GenreMapper;
import otus.model.Genre;
import otus.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @PostMapping("api/genre")
    public GenreDto saveGenre(@RequestBody GenreDto genreDto) {
        return genreMapper.toDto(genreService.saveGenre(genreMapper.toModel(genreDto)));
    }

    @GetMapping(value = {"api/genre"})
    public List<GenreDto> allGenres() {
        return genreService.getAllGenre()
                .stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("api/genre/{id}")
    public GenreDto editGenre(@PathVariable("id") long id, @RequestBody GenreDto genreDto) {
        Genre genre = genreMapper.toModel(genreDto);
        genre.setId(id);
        return genreMapper.toDto(genreService.saveGenre(genre));
    }

    @DeleteMapping("api/genre/{id}")
    public HttpStatus deleteGenre(@PathVariable("id") long id) {
        genreService.deleteGenre(genreService.getGenre(id));
        return HttpStatus.OK;
    }
}
