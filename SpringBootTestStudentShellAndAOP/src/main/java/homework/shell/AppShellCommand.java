package homework.shell;

import homework.service.TestStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor

public class AppShellCommand {
    private boolean getPersonalData;
    private final TestStudentService testStudentService;

    @ShellMethod(value = "Согласиться на обработку личных данных", key = {"y", "Y", "yes"})
    public String approveUsePersonalData() {
        this.getPersonalData = true;
        return "Вы согласились на обработку личных данных";
    }

    @ShellMethod(value = "Не согласиться на обработку личных данных", key = {"n", "N", "no"})
    public String unapprovedUsePersonalData() {
        this.getPersonalData = false;
        return "Вы отказались от обработки личных данных";
    }

    @ShellMethod(value = "Начать тестирование", key = {"r", "R", "run"})
    @ShellMethodAvailability(value = "getPersonalData")
    public void run() {
        testStudentService.startTest();
    }

    private Availability getPersonalData() {
        return !getPersonalData ? Availability.unavailable("Сначала дайте разрешение на использование личных данных") : Availability.available();
    }
}
