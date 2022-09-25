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
import java.util.Locale;

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
        verify(ioService, times(17)).outputString(anyString());
        verify(ioService, times(7)).getStringFromConsole();
    }

    @DisplayName("Проверка правильности тестовых результатов студента при правильных ответах RU")
    @Test
    void testingGoodAnswerRU() {
        testStudentService.setLocale(new Locale("ru", "RU"));
        when(ioService.getStringFromConsole()).thenReturn("2");
        TestResult testing = testStudentService.testing(student);
        assertTrue(testing.isResult());
        assertEquals(3, testing.getScore());
        assertEquals(2, testing.getWrongAnswer().size());
        assertEquals("Сколько у тебя пальцев?", testing.getWrongAnswer().get(0));
        assertEquals("Сколько цветов у светофора?", testing.getWrongAnswer().get(1));
        verify(ioService, times(5)).outputString(anyString());
    }

    @DisplayName("Проверка правильности тестовых результатов студента при правильных ответах BY")
    @Test
    void testingGoodAnswerBY() {
        testStudentService.setLocale(new Locale("be", "BY"));
        when(ioService.getStringFromConsole()).thenReturn("2");
        TestResult testing = testStudentService.testing(student);
        assertTrue(testing.isResult());
        assertEquals(3, testing.getScore());
        assertEquals(2, testing.getWrongAnswer().size());
        assertEquals("Колькі ў цябе пальцаў?", testing.getWrongAnswer().get(0));
        assertEquals("Колькі коляроў у святлафора?", testing.getWrongAnswer().get(1));
        verify(ioService, times(5)).outputString(anyString());
    }

    @DisplayName("Проверка правильности тестовых результатов студента при правильных ответах BY")
    @Test
    void testingGoodAnswerEN() {
        testStudentService.setLocale(new Locale("en", "EN"));
        when(ioService.getStringFromConsole()).thenReturn("2");
        TestResult testing = testStudentService.testing(student);
        assertTrue(testing.isResult());
        assertEquals(3, testing.getScore());
        assertEquals(2, testing.getWrongAnswer().size());
        assertEquals("How many fingers do you have?", testing.getWrongAnswer().get(0));
        assertEquals("How many colors does a traffic light have?", testing.getWrongAnswer().get(1));
        verify(ioService, times(5)).outputString(anyString());
    }

    @DisplayName("Проверка правильности тестовых результатов студента при неправильных ответах")
    @Test
    void testingWrongAnswer() {
        when(ioService.getStringFromConsole()).thenReturn("1");
        TestResult testing = testStudentService.testing(student);
        assertFalse(testing.isResult());
        assertEquals(0, testing.getScore());
        assertEquals(5, testing.getWrongAnswer().size());
        assertEquals("Один плюс один?", testing.getWrongAnswer().get(4));
        verify(ioService, times(5)).outputString(anyString());

    }

    @DisplayName("Вывод в консоль тестовых результатов студента")
    @Test
    void printResultTest() {
        testStudentService.printResultTest(testResult);
        verify(ioService, times(8)).outputString(anyString());

    }

    @DisplayName("Создание студента")
    @Test
    void getStudent() {
        when(ioService.getStringFromConsole()).thenReturn("test");
        Student student = testStudentService.getStudent();
        assertEquals("test", student.getName());
        assertEquals("test", student.getSurname());
        verify(ioService, times(4)).outputString(anyString());
        verify(ioService, times(2)).getStringFromConsole();

    }
}