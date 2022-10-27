package otus.service.ioservice.impl;


import org.springframework.stereotype.Service;
import otus.service.ioservice.IOService;

import java.io.PrintStream;
import java.util.Scanner;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class IOServiceSystemImpl implements IOService {

    private final Scanner scanner;
    private final PrintStream output;

    public IOServiceSystemImpl() {
        this.scanner = new Scanner(System.in);
        this.output = new PrintStream(System.out);
    }

    @Override
    public String getString() {
        String s;
        do {
            s = scanner.nextLine();
        } while (isBlank(s));
        return s;
    }

    @Override
    public Long getLong() {
        return scanner.nextLong();
    }

    @Override
    public void outputString(String string) {
        output.println(string);
    }
}
