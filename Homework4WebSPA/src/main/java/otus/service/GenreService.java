package otus.service;


import otus.model.Genre;

import java.util.List;

public interface GenreService {

    Genre saveGenre(Genre genre);

    Genre getGenre(long id);

    List<Genre> getAllGenre();

    Genre updateGenre(Genre genre);

    void deleteGenre(Genre genre);
}
