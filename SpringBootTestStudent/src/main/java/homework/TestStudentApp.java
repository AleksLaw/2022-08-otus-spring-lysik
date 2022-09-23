package homework;

import homework.config.AppProperties;
import homework.service.impl.TestStudentServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class TestStudentApp {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TestStudentApp.class, args);
        TestStudentServiceImpl testStudentServiceImpl = ctx.getBean(TestStudentServiceImpl.class);
        testStudentServiceImpl.startTest();
    }
}