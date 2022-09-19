package ru.otus.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Сервис тестирования студента")
@ExtendWith(MockitoExtension.class)
class TestStudentServiceImplTest {
    @Mock
    private IOServiceSystemImpl ioService;
    @Mock
    private ReaderFileCvsParserImpl readerFile;
    private TestStudentServiceImpl testStudentService;
    private TestResult testResult;
    private Student student;
    private final ArrayList<Question> questions = new ArrayList<>();

    @BeforeEach
    void setUp() {
        int needScoreForPass = 2;
        testStudentService = new TestStudentServiceImpl(readerFile, needScoreForPass, ioService);
        student = new Student("1", "1");
        testResult = new TestResult(student);
        ArrayList<String> wrongAnswer = new ArrayList<>();
        wrongAnswer.add("1");
        wrongAnswer.add("2");
        questions.add(new Question("1", List.of("2", "3"), "2"));
        questions.add(new Question("2", List.of("2", "3"), "2"));
        testResult.setWrongAnswer(wrongAnswer);
    }

    @DisplayName("Проверка правильности вызовов вывода в консоль")
    @Test
    void startTest() {
        when(ioService.getStringFromConsole()).thenReturn("2");
        when(readerFile.getQuestions()).thenReturn(questions);
        testStudentService.startTest();
        verify(ioService, times(6)).outputString(anyString());

    }

    @DisplayName("Проверка правильности тестовых результатов студента при правильных ответах")
    @Test
    void testingGoodAnswer() {
        when(readerFile.getQuestions()).thenReturn(questions);
        when(ioService.getStringFromConsole()).thenReturn("2");
        TestResult testing = testStudentService.testing(ioService, student);
        assertTrue(testing.isResult());
        assertEquals(2, testing.getScore());
        assertEquals(0, testing.getWrongAnswer().size());
    }

    @DisplayName("Проверка правильности тестовых результатов студента при неправильных ответах")
    @Test
    void testingWrongAnswer() {
        when(readerFile.getQuestions()).thenReturn(questions);
        when(ioService.getStringFromConsole()).thenReturn("1");
        TestResult testing = testStudentService.testing(ioService, student);
        assertFalse(testing.isResult());
        assertEquals(0, testing.getScore());
        assertEquals(2, testing.getWrongAnswer().size());
    }

    @DisplayName("Вывод в консоль тестовых результатов студента")
    @Test
    void printResultTest() {
        testStudentService.printResultTest(testResult, ioService);
        verify(ioService, times(5)).outputString(anyString());

    }

    @DisplayName("Создание студента")
    @Test
    void getStudent() {
        when(ioService.getStringFromConsole()).thenReturn("test");
        Student student = testStudentService.getStudent(ioService);
        assertEquals("test", student.getName());
        assertEquals("test", student.getSurname());

    }
}