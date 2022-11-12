package otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.model.Author;
import otus.service.domenservice.AuthorService;
import otus.service.ioservice.IOService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandAuthor {

    public static final String NOT_FIND = "____________________ Не найден ____________________";
    public static final String INPUT_SURNAME = "Введите фамилию";
    public static final String INPUT_ID = "Введите id автора";
    public static final String INPUT_NAME = "Введите имя";
    public static final String DELETED = " удален";
    public static final String NOT_DELETED = " Не удален т.к. он является автором существующей книги";
    public static final String ADDED = " добавлен";
    public static final String UPDATED = " обновлен";

    private final AuthorService authorService;
    private final IOService ioService;

    @ShellMethod(value = "Сохранение автора", key = {"sa", "SaveAuthor"})
    public void saveAuthor() {
        Author author = authorService.saveAuthor(
                ioService.getString(INPUT_NAME), ioService.getString(INPUT_SURNAME));
        ioService.outputString(author + ADDED);
    }

    @ShellMethod(value = "Получение всех авторов", key = {"aa", "AllAuthor"})
    public void getAllAuthors() {
        authorService.getAllAuthor().forEach(author -> ioService.outputString(author.toString()));
    }

    @ShellMethod(value = "Получение автора", key = {"ga", "GetAuthor"})
    public void getAuthor() {
        Author author = authorService.getAuthor(ioService.getLong(INPUT_ID));
        if (author == null) {
            ioService.outputString(NOT_FIND);
            getAllAuthors();
        } else {
            ioService.outputString(author.toString());
        }
    }

    @ShellMethod(value = "Обновление автора", key = {"ua", "UpdateAuthor"})
    public void updateAuthor() {
        Author author = authorService.getAuthor(ioService.getLong(INPUT_ID));
        if (author == null) {
            ioService.outputString(NOT_FIND);
            getAllAuthors();
        } else {
            author.setName(ioService.getString(INPUT_NAME));
            author.setSurname(ioService.getString(INPUT_SURNAME));
            ioService.outputString(authorService.updateAuthor(author) + UPDATED);
        }
    }

    @ShellMethod(value = "Удаление автора", key = {"da", "DeleteAuthor"})
    public void deleteAuthor() {
        Author author = authorService.getAuthor(ioService.getLong(INPUT_ID));
        if (author == null) {
            ioService.outputString(NOT_FIND);
            getAllAuthors();
        } else {
            try {
                authorService.deleteAuthor(author);
                ioService.outputString(author + DELETED);
            } catch (Exception e) {
                ioService.outputString(author + NOT_DELETED);
            }
        }
    }
}
