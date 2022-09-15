package ru.otus.homework.service.impl;

import ru.otus.homework.readerFile.impl.ReaderFileCvsParserImpl;
import ru.otus.homework.service.TestStudentService;

public class TestStudentServiceImpl implements TestStudentService {
    private final ReaderFileCvsParserImpl readerFile;

    public TestStudentServiceImpl(ReaderFileCvsParserImpl readerFile) {
        this.readerFile = readerFile;
    }

    @Override
    public void printQuestions() {
        readerFile
                .getQuestions()
                .forEach(question ->
                        System.out.printf("Question-%s, answers-%s, correct answer -%s %n",
                                question.getQuestionText(),
                                question.getAnswers(),
                                question.getCorrectAnswer()));
    }
}
