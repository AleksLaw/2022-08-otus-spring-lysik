package homework.service.impl;

import homework.config.AppProperties;
import homework.domain.Question;
import homework.domain.Student;
import homework.domain.TestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static homework.utils.Constants.FILENAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Сервис тестирования студента без поднятия контекста")
@ExtendWith(MockitoExtension.class)
class TestStudentServiceImplTestWithoutContext {
    @Mock
    private IOServiceSystemImpl ioService;
    @Mock
    private ReaderFileCvsParserImpl readerFile;
    @Mock
    private MessageSource messageSource;
    private TestStudentServiceImpl testStudentService;
    private TestResult testResult;
    private Student student;
    private final ArrayList<Question> questions = new ArrayList<>();

    @BeforeEach
    void setUp() {
        AppProperties appProperties = new AppProperties();
        appProperties.setNeedScoreForPass(2);
        appProperties.setLocale(new Locale("ru", "RU"));
        lenient().when(messageSource.getMessage(FILENAME, null, appProperties.getLocale())).thenReturn("Questions_ru.csv");
        testStudentService = new TestStudentServiceImpl(readerFile, appProperties, ioService, messageSource);
        student = new Student("1", "1");
        testResult = new TestResult(student);
        ArrayList<String> wrongAnswer = new ArrayList<>();
        wrongAnswer.add("1");
        wrongAnswer.add("2");
        questions.add(new Question("1", List.of("2", "3"), "2"));
        questions.add(new Question("2", List.of("2", "3"), "2"));
        testResult.setWrongAnswer(wrongAnswer);
    }

    @DisplayName("Проверка количества вызовов ввода/вывода в консоль")
    @Test
    void startTest() {
        when(ioService.getStringFromConsole()).thenReturn("2");
        when(readerFile.getQuestions("Questions_ru.csv")).thenReturn(questions);
        testStudentService.startTest();
        verify(ioService, times(4)).outputString(anyString());
        verify(ioService, times(4)).getStringFromConsole();
    }

    @DisplayName("Проверка правильности тестовых результатов студента при правильных ответах")
    @Test
    void testingGoodAnswer() {
        when(readerFile.getQuestions("Questions_ru.csv")).thenReturn(questions);
        when(ioService.getStringFromConsole()).thenReturn("2");
        TestResult testing = testStudentService.testing(ioService, student);
        assertTrue(testing.isResult());
        assertEquals(2, testing.getScore());
        assertEquals(0, testing.getWrongAnswer().size());
    }

    @DisplayName("Проверка правильности тестовых результатов студента при неправильных ответах")
    @Test
    void testingWrongAnswer() {
        when(readerFile.getQuestions("Questions_ru.csv")).thenReturn(questions);
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