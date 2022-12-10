package otus;


import com.github.cloudyrock.spring.v5.EnableMongock;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
@RequiredArgsConstructor
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
