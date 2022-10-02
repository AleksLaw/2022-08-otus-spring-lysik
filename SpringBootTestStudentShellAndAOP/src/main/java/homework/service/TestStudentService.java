package homework.service;

import homework.domain.Student;
import homework.domain.TestResult;

public interface TestStudentService {
    void startTest();

    TestResult testing(Student student);

    Student getStudent();
}
