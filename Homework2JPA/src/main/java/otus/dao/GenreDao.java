package otus.dao;


import otus.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    Genre save(Genre genre);

    Genre update(Genre genre);

    Optional<Genre> getById(long id);

    List<Genre> getAll();

    void deleteById(long id);
}
