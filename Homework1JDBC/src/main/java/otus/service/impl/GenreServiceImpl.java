package otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.dao.GenreDao;
import otus.model.Genre;
import otus.service.GenreService;
import otus.service.IOService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    public static final String SAVE_GENRE = "Сохранение жанра\n";
    public static final String INPUT_NAME = "Ведите название";
    public static final String INPUT_ID_GENRE = "Введите id жанра";
    public static final String GENRE_COUNT = "Сейчас у нас количество жанров = ";
    public static final String DELETED = " удален";
    public static final String GENRE_WITH_ID = "Жанр с ид = ";
    public static final String NOT_FIND = "Нет такого! создаем!";
    private final GenreDao genreDAO;
    private final IOService ioService;

    @Override
    public long saveGenre() {
        ioService.outputString(SAVE_GENRE);
        long id = genreDAO.insert(createGenre());
        ioService.outputString(getGenreById(id).toString());
        return id;
    }

    @Override
    public Genre getGenre() {
        getAllGenre();
        ioService.outputString(INPUT_ID_GENRE);
        Genre genre = genreDAO.getById(ioService.getLong());
        ioService.outputString(genre.toString());
        return genre;
    }

    @Override
    public Genre getGenreById(long id) {
        return genreDAO.getById(id);
    }

    @Override
    public List<Genre> getAllGenre() {
        List<Genre> all = genreDAO.getAll();
        all.forEach(s -> ioService.outputString(s.toString()));
        return all;
    }

    @Override
    public Long updateGenre() {
        Long id = checkId();
        Genre genre = createGenre();
        genre.setId(id);
        long rez = genreDAO.update(genre);
        ioService.outputString(getGenreById(id).toString());
        return rez;
    }

    @Override
    public long countGenre() {
        long count = genreDAO.count();
        ioService.outputString(GENRE_COUNT + count);
        return count;
    }

    @Override
    public long deleteGenreById(long id) {
        long rez = genreDAO.deleteById(id);
        ioService.outputString(GENRE_WITH_ID + id + DELETED);
        return rez;
    }

    @Override
    public long deleteGenre() {
        return deleteGenreById(checkId());
    }

    @Override
    public Genre getOrSaveGenre() {
        ioService.outputString(INPUT_ID_GENRE);
        if (getAllGenre().isEmpty()) {
            return getGenreById(saveGenre());
        } else {
            try {
                return getGenreById(ioService.getLong());
            } catch (Exception e) {
                ioService.outputString(NOT_FIND);
                return getGenreById(saveGenre());
            }
        }
    }

    private Genre createGenre() {
        ioService.outputString(INPUT_NAME);
        return new Genre(ioService.getString());
    }

    private Long checkId() {
        List<Long> collect = getAllGenre().stream().map(Genre::getId).collect(Collectors.toList());
        Long id;
        do {
            ioService.outputString(INPUT_ID_GENRE);
            id = ioService.getLong();
        } while (!collect.contains(id));
        return id;
    }
}
