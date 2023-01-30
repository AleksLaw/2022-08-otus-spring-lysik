package otus;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@RequiredArgsConstructor
@EnableCircuitBreaker
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
