package otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.service.domenservice.AuthorService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandAuthor {
    private final AuthorService authorService;

    @ShellMethod(value = "Сохранение автора", key = {"sa", "SaveAuthor"})
    public void saveAuthor() {
        authorService.saveAuthor();
    }

    @ShellMethod(value = "Получение всех авторов", key = {"aa", "AllAuthor"})
    public void getAllAuthors() {
        authorService.getAllAuthor();
    }

    @ShellMethod(value = "Получение автора", key = {"ga", "GetAuthor"})
    public void getAuthor() {
        authorService.getAuthor();
    }

    @ShellMethod(value = "Обновление автора", key = {"ua", "UpdateAuthor"})
    public void updateAuthor() {
        authorService.updateAuthor();
    }

    @ShellMethod(value = "Удаление автора", key = {"da", "DeleteAuthor"})
    public void deleteAuthor() {
        authorService.deleteAuthor();
    }
}
