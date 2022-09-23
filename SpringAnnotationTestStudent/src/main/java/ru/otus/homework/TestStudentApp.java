package ru.otus.homework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.homework.service.impl.TestStudentServiceImpl;

@ComponentScan
public class TestStudentApp {

    public static void main(String[] args) {
        runTestStudent();
    }

    private static void runTestStudent() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestStudentApp.class);
        applicationContext.getBean(TestStudentServiceImpl.class).startTest();
    }

}