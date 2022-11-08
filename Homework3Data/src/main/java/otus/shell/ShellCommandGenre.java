package otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.model.Genre;
import otus.service.domenservice.GenreService;
import otus.service.ioservice.IOService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandGenre {

    public static final String NOT_FIND = "____________________ Не найден ____________________";
    public static final String INPUT_NAME = "Введите название";
    public static final String INPUT_ID = "Введите id жанра";
    public static final String DELETED = " удален";
    public static final String NOT_DELETED = " Не удален т.к. он является жанром существующей книги";
    public static final String ADDED = " добавлен";
    public static final String UPDATED = " обновлен";

    private final GenreService genreService;
    private final IOService ioService;

    @ShellMethod(value = "Сохранение жанра", key = {"sg", "SaveGenre"})
    public void saveGenre() {
        Genre genre = genreService.saveGenre(ioService.getString(INPUT_NAME));
        ioService.outputString(genre + ADDED);
    }

    @ShellMethod(value = "Получение всех жанров", key = {"ag", "AllGenre"})
    public void getAllGenre() {
        genreService.getAllGenre().forEach(genre -> ioService.outputString(genre.toString()));
    }

    @ShellMethod(value = "Получение жанра", key = {"gg", "GetGenre"})
    public void getGenre() {
        Genre genre = genreService.getGenre(ioService.getLong(INPUT_ID));
        if (genre == null) {
            ioService.outputString(NOT_FIND);
            getAllGenre();
        } else {
            ioService.outputString(genre.toString());
        }
    }

    @ShellMethod(value = "Обновление жанра", key = {"ug", "UpdateGenre"})
    public void updateGenre() {
        Genre genre = genreService.getGenre(ioService.getLong(INPUT_ID));
        if (genre == null) {
            ioService.outputString(NOT_FIND);
            getAllGenre();
        } else {
            genre.setName(ioService.getString(INPUT_NAME));
            ioService.outputString(genreService.updateGenre(genre) + UPDATED);
        }
    }

    @ShellMethod(value = "Удаление жанра", key = {"dg", "DeleteGenre"})
    public void deleteGenre() {
        Genre genre = genreService.getGenre(ioService.getLong(INPUT_ID));
        if (genre == null) {
            ioService.outputString(NOT_FIND);
            getAllGenre();
        } else {
            try {
                genreService.deleteGenre(genre);
                ioService.outputString(genre + DELETED);
            } catch (Exception e) {
                ioService.outputString(genre + NOT_DELETED);
            }
        }
    }
}
