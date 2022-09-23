package homework.service.impl;

import homework.config.AppProperties;
import homework.service.IOService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

import static homework.utils.Constants.TRY_AGAIN;

@Service
public class IOServiceSystemImpl implements IOService {

    private final Scanner scanner;
    private final PrintStream output;
    private final MessageSource messageSource;
    private final AppProperties appProperties;

    public IOServiceSystemImpl(MessageSource messageSource, AppProperties appProperties) {
        this.messageSource = messageSource;
        this.appProperties = appProperties;
        this.scanner = new Scanner(System.in);
        this.output = new PrintStream(System.out);
    }

    @Override
    public String getStringFromConsole() {
        String stringFromConsole = scanner.nextLine();
        if (stringFromConsole.isEmpty()) {
            outputString(TRY_AGAIN, null);
            getStringFromConsole();
        }
        return stringFromConsole;
    }

    @Override
    public void close() {
        scanner.close();
    }

    @Override
    public void outputString(String string, String[] argsMessageSource) {
        output.println(messageSource.getMessage(string, argsMessageSource, appProperties.getLocale()));
    }

    @Override
    public void outputString(String string) {
        output.println(string);
    }

}
