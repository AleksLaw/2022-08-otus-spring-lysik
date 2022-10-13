package otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.service.BookService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandBook {
    private final BookService bookService;

    @ShellMethod(value = "Сохранение книги", key = {"sb", "SaveBook"})
    public void saveBook() {
        bookService.saveBook();
    }

    @ShellMethod(value = "Получение всех книг", key = {"ab", "AllBooks"})
    public void getAllBook() {
        bookService.getAllBook();
    }

    @ShellMethod(value = "Получение книги", key = {"gb", "GetBook"})
    public void getBooks() {
        bookService.getBook();
    }

    @ShellMethod(value = "Обновление книги", key = {"ub", "UpdateBook"})
    public void updateBook() {
        bookService.updateBook();
    }

    @ShellMethod(value = "Количество книг", key = {"cb", "CountBook"})
    public void countBook() {
        bookService.countBook();
    }

    @ShellMethod(value = "Удаление книги", key = {"db", "DeleteBook"})
    public void deleteBook() {
        bookService.deleteBook();
    }
}
