package otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.service.domenservice.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandGenre {
    private final GenreService genreService;

    @ShellMethod(value = "Сохранение жанра", key = {"sg", "SaveGenre"})
    public void saveGenre() {
        genreService.saveGenre();
    }

    @ShellMethod(value = "Получение всех жанров", key = {"ag", "AllGenre"})
    public void getAllGenre() {
        genreService.getAllGenre();
    }

    @ShellMethod(value = "Получение жанра", key = {"gg", "GetGenre"})
    public void getGenre() {
        genreService.getGenre();
    }

    @ShellMethod(value = "Обновление жанра", key = {"ug", "UpdateGenre"})
    public void updateGenre() {
        genreService.updateGenre();
    }

    @ShellMethod(value = "Удаление жанра", key = {"dg", "DeleteGenre"})
    public void deleteGenre() {
        genreService.deleteGenre();
    }
}
