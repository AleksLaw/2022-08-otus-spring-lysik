package otus.service.domenservice.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.dao.GenreDao;
import otus.model.Genre;
import otus.service.domenservice.GenreService;
import otus.service.ioservice.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    public static final String SAVE_GENRE = "Сохранение жанра\n";
    public static final String INPUT_NAME = "Введите название";
    public static final String INPUT_ID_GENRE = "Введите id жанра";
    public static final String DELETED = " удален";
    public static final String GENRE_WITH_ID = "Жанр с ид = ";
    public static final String NOT_DELETED = "Жанр не может быть удален. Он является жанром книги которая у нас есть";
    public static final String NOT_FIND = "Не найден";
    private final GenreDao genreDAO;
    private final IOService ioService;

    @Override
    @Transactional
    public Genre saveGenre() {
        ioService.outputString(SAVE_GENRE);
        Genre genre = genreDAO.save(createGenre());
        ioService.outputString(genre.toString());
        return genre;
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenre() {
        Genre genre = genreDAO.getById(getId()).orElse(null);
        if (genre == null) {
            ioService.outputString(NOT_FIND);
            getAllGenre();
        } else {
            ioService.outputString(genre.toString());
        }
        return genre;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAllGenre() {
        List<Genre> allGenre = genreDAO.getAll();
        allGenre.forEach(genre -> ioService.outputString(genre.toString()));
        return allGenre;
    }

    @Override
    @Transactional
    public Genre updateGenre() {
        Long id = checkGenre().getId();
        Genre genre = createGenre();
        genre.setId(id);
        Genre update = genreDAO.update(genre);
        ioService.outputString(update.toString());
        return update;
    }


    @Override
    @Transactional
    public long deleteGenre() {
        Genre checkGenre = checkGenre();
        long rez = 0;
        try {
            rez = genreDAO.deleteById(checkGenre.getId());
            ioService.outputString(GENRE_WITH_ID + checkGenre.getId() + DELETED);
        } catch (RuntimeException e) {
            ioService.outputString(NOT_DELETED);
        }
        return rez;
    }

    private Genre createGenre() {
        ioService.outputString(INPUT_NAME);
        return new Genre(ioService.getString());
    }

    private long getId() {
        ioService.outputString(INPUT_ID_GENRE);
        return ioService.getLong();
    }

    private Genre checkGenre() {
        Genre genre;
        do {
            genre = genreDAO.getById(getId()).orElse(null);
            if (genre == null) {
                ioService.outputString(NOT_FIND);
                getAllGenre();
            }
        } while (genre == null);
        return genre;
    }
}
