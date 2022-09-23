package homework.service.impl;

import homework.domain.Student;
import homework.domain.TestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Сервис тестирования студента с поднятием контекста")
@SpringBootTest
class TestStudentServiceImplTest {
    @MockBean
    private IOServiceSystemImpl ioService;
    @Autowired
    private TestStudentServiceImpl testStudentService;
    private TestResult testResult;
    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student("1", "1");
        testResult = new TestResult(student);
        ArrayList<String> wrongAnswer = new ArrayList<>();
        wrongAnswer.add("1");
        wrongAnswer.add("2");
        testResult.setWrongAnswer(wrongAnswer);
    }

    @DisplayName("Проверка количества вызовов ввода/вывода в консоль")
    @Test
    void startTest() {
        when(ioService.getStringFromConsole()).thenReturn("2");
        testStudentService.startTest();
        verify(ioService, times(10)).outputString(anyString(), any());
        verify(ioService, times(7)).outputString(anyString());
        verify(ioService, times(7)).getStringFromConsole();
    }

    @DisplayName("Проверка правильности тестовых результатов студента при правильных ответах")
    @Test
    void testingGoodAnswer() {
        when(ioService.getStringFromConsole()).thenReturn("2");
        TestResult testing = testStudentService.testing(ioService, student);
        assertTrue(testing.isResult());
        assertEquals(3, testing.getScore());
        assertEquals(2, testing.getWrongAnswer().size());
        assertEquals("Сколько у тебя пальцев?", testing.getWrongAnswer().get(0));
        assertEquals("Сколько цветов у светофора?", testing.getWrongAnswer().get(1));
        verify(ioService, times(5)).outputString(anyString(), any());
    }

    @DisplayName("Проверка правильности тестовых результатов студента при неправильных ответах")
    @Test
    void testingWrongAnswer() {
        when(ioService.getStringFromConsole()).thenReturn("1");
        TestResult testing = testStudentService.testing(ioService, student);
        assertFalse(testing.isResult());
        assertEquals(0, testing.getScore());
        assertEquals(5, testing.getWrongAnswer().size());
        assertEquals("Один плюс один?", testing.getWrongAnswer().get(4));
        verify(ioService, times(5)).outputString(anyString(), any());

    }

    @DisplayName("Вывод в консоль тестовых результатов студента")
    @Test
    void printResultTest() {
        testStudentService.printResultTest(testResult, ioService);
        verify(ioService, times(3)).outputString(anyString(), any());
        verify(ioService, times(5)).outputString(anyString());

    }

    @DisplayName("Создание студента")
    @Test
    void getStudent() {
        when(ioService.getStringFromConsole()).thenReturn("test");
        Student student = testStudentService.getStudent(ioService);
        assertEquals("test", student.getName());
        assertEquals("test", student.getSurname());
        verify(ioService, times(2)).outputString(anyString());
        verify(ioService, times(2)).outputString(anyString(), any());

    }
}