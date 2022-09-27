package homework.service.impl;

import homework.service.IOService;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

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
        return scanner.nextLine();
    }

    @Override
    public void outputString(String string) {
        output.println(string);
    }

    @Override
    public void close() {
        scanner.close();
    }

}
