package otus.service;


import otus.model.Genre;

import java.util.List;

public interface GenreService {
    long saveGenre();

    Genre getGenreById(long id);

    Genre getGenre();

    List<Genre> getAllGenre();

    Genre getOrSaveGenre();

    Long updateGenre();

    long countGenre();

    long deleteGenreById(long id);

    long deleteGenre();
}
