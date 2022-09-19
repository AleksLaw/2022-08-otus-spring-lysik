package ru.otus.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.homework.service.IOService;

import java.io.PrintStream;
import java.util.Scanner;

import static ru.otus.homework.utils.Constants.TRY_AGAIN;

@Service
public class IOServiceSystemImpl implements IOService {

    private final Scanner scanner;
    private final PrintStream output;

    public IOServiceSystemImpl() {
        this.scanner = new Scanner(System.in);
        this.output = new PrintStream(System.out);
    }

    @Override
    public String getStringFromConsole() {

        String stringFromConsole = scanner.nextLine();
        if (stringFromConsole.isEmpty()) {
            outputString(TRY_AGAIN);
            getStringFromConsole();
        }
        return stringFromConsole;
    }

    @Override
    public void close() {
        scanner.close();
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }

}
