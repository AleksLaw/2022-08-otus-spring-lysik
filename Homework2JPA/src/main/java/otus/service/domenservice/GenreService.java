package otus.service.domenservice;


import otus.model.Genre;

import java.util.List;

public interface GenreService {

    Genre saveGenre(String name);

    Genre getGenre(long id);

    List<Genre> getAllGenre();

    Genre updateGenre(Genre genre);

    void deleteGenre(Genre genre);
}
