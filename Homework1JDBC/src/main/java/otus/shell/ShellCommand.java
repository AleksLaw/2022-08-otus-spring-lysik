package otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import otus.service.BookService;

/**
 * @author Aleksander Lysik
 * @since 07.10.2022
 */
@ShellComponent
@RequiredArgsConstructor
public class ShellCommand {
    private final BookService bookService;

    @ShellMethod(value = "Начать тестирование", key = {"r", "R", "run"})
    @ShellMethodAvailability(value = "11111111111")
    public void run() {
        bookService.saveBook();
    }
}
