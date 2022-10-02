package homework.service.impl;

import homework.config.AppProperties;
import homework.domain.Question;
import homework.domain.Student;
import homework.domain.TestResult;
import homework.service.IOService;
import homework.service.TestStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Locale;

import static java.lang.String.valueOf;

@Service
@RequiredArgsConstructor
public class TestStudentServiceImpl implements TestStudentService {
    public static final String SEPARATOR = "########################################";
    public static final String STUDENT_GOT_SCORE = "student.score";
    public static final String QUESTIONS_AND_ANSWERS = "questions.and.answers";
    public static final String STUDENT_PASSED = "student.passed";
    public static final String STUDENT_FAIL = "student.not.passed";
    public static final String INPUT_NAME = "input.name";
    public static final String INPUT_SURNAME = "input.surname";
    public static final String WRONG_ANSWERS_TO_QUESTIONS = "wrong.answers";
    private final ReaderFileCvsParserImpl readerFile;
    private final IOService ioService;
    private final MessageSource messageSource;
    private final AppProperties appProp;

    @Override
    public void startTest() {
        Student student = getStudent();
        TestResult testResult = testing(student);
        printResultTest(testResult);
    }

    @Override
    public TestResult testing(Student student) {
        TestResult testResult = new TestResult(student);
        int studentScore = 0;
        ArrayList<String> wrongAnswer = new ArrayList<>();
        for (Question question : readerFile.getQuestions(appProp.getFilename())) {
            printQuestionWithAnswerOption(question);
            studentScore++;
            if (!ioService.getStringFromConsole().equals(question.getCorrectAnswer())) {
                wrongAnswer.add(question.getQuestionText());
                studentScore--;
            }
        }
        testResult.setScore(studentScore);
        testResult.setResult(appProp.getNeedScoreForPass() <= testResult.getScore());
        testResult.setWrongAnswer(wrongAnswer);
        return testResult;
    }

    @Override
    public Student getStudent() {
        Locale locale = appProp.getLocale();
        printSeparator();
        ioService.outputString(messageSource.getMessage(INPUT_NAME, null, locale));
        String name = ioService.getStringFromConsole();
        ioService.outputString(messageSource.getMessage(INPUT_SURNAME, null, locale));
        String surname = ioService.getStringFromConsole();
        printSeparator();
        return new Student(name, surname);
    }

    private void printSeparator() {
        ioService.outputString(SEPARATOR);
    }

    private void printQuestionWithAnswerOption(Question question) {
        ioService.outputString(
                messageSource.getMessage(
                        QUESTIONS_AND_ANSWERS,
                        new String[]{question.getQuestionText(), question.getListAnswers().toString()},
                        appProp.getLocale()
                ));
    }

    private void printResultTest(TestResult result) {
        Student student = result.getStudent();
        Locale locale = appProp.getLocale();
        printSeparator();
        ioService.outputString(
                messageSource.getMessage(
                        STUDENT_GOT_SCORE,
                        new String[]{student.toString(), valueOf(result.getScore()), valueOf(appProp.getNeedScoreForPass())},
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
        printSeparator();
        if (result.getWrongAnswer().size() > 0) {
            ioService.outputString(
                    messageSource.getMessage(
                            WRONG_ANSWERS_TO_QUESTIONS, null, locale));
            result.getWrongAnswer().forEach(ioService::outputString);
            printSeparator();
        }
    }
}
