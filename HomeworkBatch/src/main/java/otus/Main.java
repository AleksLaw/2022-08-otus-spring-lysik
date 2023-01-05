package otus;

import com.github.cloudyrock.spring.v5.EnableMongock;
import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@EnableMongock
@SpringBootApplication
@RequiredArgsConstructor
public class Main {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
        Console.main(args);
    }
}
