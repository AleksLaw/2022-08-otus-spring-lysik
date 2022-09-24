package homework.service.impl;

import homework.config.AppProperties;
import homework.domain.Question;
import homework.domain.Student;
import homework.domain.TestResult;
import homework.service.IOService;
import homework.service.TestStudentService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static homework.utils.Constants.*;
import static java.lang.String.valueOf;

@Service
public class TestStudentServiceImpl implements TestStudentService {
    private final ReaderFileCvsParserImpl readerFile;
    private final int needScore;
    private final IOService ioService;
    private final MessageSource messageSource;
    private Locale locale;

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public TestStudentServiceImpl(ReaderFileCvsParserImpl reader, AppProperties appProp, IOService ioService, MessageSource messageSource) {
        this.readerFile = reader;
        this.needScore = appProp.getNeedScoreForPass();
        this.ioService = ioService;
        this.messageSource = messageSource;
        this.locale = appProp.getLocale();
    }

    @Override
    public void startTest() {
        try (ioService) {
            Student student = getStudent(ioService);
            TestResult testResult = testing(ioService, student);
            printResultTest(testResult, ioService);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TestResult testing(IOService ioService, Student student) {
        TestResult testResult = new TestResult(student);
        int studentScore = 0;
        ArrayList<String> wrongAnswer = new ArrayList<>();
        for (Question question : readerFile.getQuestions(messageSource.getMessage(FILENAME, null, locale))) {
            printQuestionWithAnswerOption(question, ioService);
            studentScore++;
            if (!ioService.getStringFromConsole().equals(question.getCorrectAnswer())) {
                wrongAnswer.add(question.getQuestionText());
                studentScore--;
            }
        }
        testResult.setScore(studentScore);
        testResult.setResult(needScore <= testResult.getScore());
        testResult.setWrongAnswer(wrongAnswer);
        return testResult;
    }

    public void printResultTest(TestResult result, IOService ioService) {
        Student student = result.getStudent();
        printSeparator(ioService);
        ioService.outputString(
                messageSource.getMessage(
                        STUDENT_GOT_SCORE,
                        new String[]{student.toString(), valueOf(result.getScore()), valueOf(needScore)},
                        locale
                ));
        ioService.outputString(
                messageSource.getMessage(
                        result.isResult()
                                ? STUDENT_PASSED
                                : STUDENT_FAIL,
                        new String[]{student.toString()},
                        locale
                ));
        printSeparator(ioService);
        if (result.getWrongAnswer().size() > 0) {
            ioService.outputString(
                    messageSource.getMessage(
                            WRONG_ANSWERS_TO_QUESTIONS, null, locale));
            result.getWrongAnswer().forEach(ioService::outputString);
            printSeparator(ioService);
        }
    }

    public Student getStudent(IOService ioService) {
        printSeparator(ioService);
        ioService.outputString(
                messageSource.getMessage(INPUT_NAME, null, locale));
        String name = ioService.getStringFromConsole();
        ioService.outputString(
                messageSource.getMessage(INPUT_SURNAME, null, locale));
        String surname = ioService.getStringFromConsole();
        printSeparator(ioService);
        return new Student(name, surname);
    }

    private static void printSeparator(IOService ioService) {
        ioService.outputString(SEPARATOR);
    }

    private void printQuestionWithAnswerOption(Question question, IOService ioService) {
        ioService.outputString(
                messageSource.getMessage(
                        QUESTIONS_AND_ANSWERS,
                        new String[]{question.getQuestionText(), question.getListAnswers().toString()},
                        locale
                ));
    }
}
