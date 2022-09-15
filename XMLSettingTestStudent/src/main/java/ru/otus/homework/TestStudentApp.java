package ru.otus.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.service.impl.TestStudentServiceImpl;

public class TestStudentApp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-context.xml");
        applicationContext.getBean(TestStudentServiceImpl.class).printQuestions();
        applicationContext.close();
    }

}
