package otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.service.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandGenre {
    private final GenreService genreService;

    @ShellMethod(value = "Сохранение жанра", key = {"sg", "SaveGenre"})
    public void saveBook() {
        genreService.saveGenre();
    }

    @ShellMethod(value = "Получение всех жанров", key = {"ag", "AllGenre"})
    public void getAllBook() {
        genreService.getAllGenre();
    }

    @ShellMethod(value = "Получение жанра", key = {"gg", "GetGenre"})
    public void getBooks() {
        genreService.getGenre();
    }

    @ShellMethod(value = "Обновление жанра", key = {"ug", "UpdateGenre"})
    public void updateBook() {
        genreService.updateGenre();
    }

    @ShellMethod(value = "Количество жанров", key = {"cg", "CountGenre"})
    public void countBook() {
        genreService.countGenre();
    }

    @ShellMethod(value = "Удаление жанра", key = {"dg", "DeleteGenre"})
    public void deleteBook() {
        genreService.deleteGenre();
    }
}
