package otus.service.domenservice;


import otus.model.Genre;

import java.util.List;

public interface GenreService {
    Genre saveGenre();

    Genre getGenre();

    List<Genre> getAllGenre();

    Genre updateGenre();

    long deleteGenre();
}
