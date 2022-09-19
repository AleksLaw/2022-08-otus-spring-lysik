package ru.otus.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.service.IOService;
import ru.otus.homework.service.TestStudentService;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.String.format;
import static ru.otus.homework.utils.Constants.*;

@Service
@PropertySource("classpath:application.properties")
public class TestStudentServiceImpl implements TestStudentService {
    private final ReaderFileCvsParserImpl readerFile;
    private final int needScoreForPass;
    private final IOService ioService;

    @Autowired
    public TestStudentServiceImpl(ReaderFileCvsParserImpl readerFile, @Value("${needScoreForPass}") int needScoreForPass, IOService ioService) {
        this.readerFile = readerFile;
        this.needScoreForPass = needScoreForPass;
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
        testResult.setResult(needScoreForPass <= testResult.getScore());
        testResult.setWrongAnswer(wrongAnswer);
        return testResult;
    }

    private void printQuestionWithAnswerOption(Question question, IOService ioService) {
        ioService.outputString(format(TEXT_QUESTION_AND_ANSWER_OPTIONS, question.getQuestionText(), question.getListAnswers()));
    }

    public void printResultTest(TestResult testResult, IOService ioService) {
        Student student = testResult.getStudent();
        ioService.outputString(format
                (STUDENT_NAME_SURNAME_GOT_SCORE, student.getName(), student.getSurname(), testResult.getScore(), needScoreForPass));
        if (testResult.getWrongAnswer().size() > 0) {
            ioService.outputString(WRONG_ANSWERS_TO_QUESTIONS);
            testResult.getWrongAnswer().forEach(ioService::outputString);
        }
        ioService.outputString(format(testResult.isResult() ? STUDENT_PASSED : STUDENT_DID_NOT_PASS, student.getName(), student.getSurname()));
    }

    public Student getStudent(IOService ioService) {
        ioService.outputString(INPUT_NAME);
        String name = ioService.getStringFromConsole();
        ioService.outputString(INPUT_SURNAME);
        String surname = ioService.getStringFromConsole();
        return new Student(name, surname);
    }
}
