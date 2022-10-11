package otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.service.AuthorService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandAuthor {
    private final AuthorService authorService;

    @ShellMethod(value = "Сохранение автора", key = {"sa", "SaveAuthor"})
    public void saveBook() {
        authorService.saveAuthor();
    }

    @ShellMethod(value = "Получение всех авторов", key = {"aa", "AllAuthor"})
    public void getAllBook() {
        authorService.getAllAuthor();
    }

    @ShellMethod(value = "Получение автора", key = {"ga", "GetAuthor"})
    public void getBooks() {
        authorService.getAuthor();
    }

    @ShellMethod(value = "Обновление автора", key = {"ua", "UpdateAuthor"})
    public void updateBook() {
        authorService.updateAuthor();
    }

    @ShellMethod(value = "Количество авторов", key = {"ca", "CountAuthor"})
    public void countBook() {
        authorService.countAuthor();
    }

    @ShellMethod(value = "Удаление автора", key = {"da", "DeleteAuthor"})
    public void deleteBook() {
        authorService.deleteAuthor();
    }
}
