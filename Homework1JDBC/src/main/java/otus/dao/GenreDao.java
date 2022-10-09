package otus.dao;


import otus.model.Genre;

import java.util.List;

public interface GenreDao {
    long count();

    long insert(Genre genre);

    long update(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    int deleteById(long id);
}
