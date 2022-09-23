package homework.service.impl;

import homework.config.AppProperties;
import homework.domain.Question;
import homework.domain.Student;
import homework.domain.TestResult;
import homework.service.IOService;
import homework.service.TestStudentService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

import static homework.utils.Constants.*;
import static java.lang.String.valueOf;

@Service
public class TestStudentServiceImpl implements TestStudentService {
    private final ReaderFileCvsParserImpl readerFile;
    private final int needScore;
    private final IOService ioService;

    public TestStudentServiceImpl(ReaderFileCvsParserImpl reader, AppProperties appProp, IOService ioService) {
        this.readerFile = reader;
        this.needScore = appProp.getNeedScoreForPass();
        this.ioService = ioService;
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
        for (Question question : readerFile.getQuestions()) {
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
                STUDENT_GOT_SCORE,
                new String[]{student.toString(), valueOf(result.getScore()), valueOf(needScore)}
        );
        ioService.outputString(
                result.isResult()
                        ? STUDENT_PASSED
                        : STUDENT_FAIL,
                new String[]{student.toString()}
        );
        printSeparator(ioService);
        if (result.getWrongAnswer().size() > 0) {
            ioService.outputString(WRONG_ANSWERS_TO_QUESTIONS, null);
            result.getWrongAnswer().forEach(ioService::outputString);
            printSeparator(ioService);
        }
    }

    public Student getStudent(IOService ioService) {
        printSeparator(ioService);
        ioService.outputString(INPUT_NAME, null);
        String name = ioService.getStringFromConsole();
        ioService.outputString(INPUT_SURNAME, null);
        String surname = ioService.getStringFromConsole();
        printSeparator(ioService);
        return new Student(name, surname);
    }

    private static void printSeparator(IOService ioService) {
        ioService.outputString(SEPARATOR);
    }

    private void printQuestionWithAnswerOption(Question question, IOService ioService) {
        ioService.outputString(
                QUESTIONS_AND_ANSWERS,
                new String[]{question.getQuestionText(), question.getListAnswers().toString()}
        );
    }
}
