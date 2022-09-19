package ru.otus.homework.service;

import org.junit.jupiter.api.Test;
import ru.otus.homework.common.Question;
import ru.otus.homework.readerFile.ReaderFile;
import ru.otus.homework.readerFile.impl.ReaderFileCvsParserImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestStudentServiceTestImpl {

    @Test
    void assertGood() {
        ReaderFile readerFile = new ReaderFileCvsParserImpl("Questions.csv");
        List<Question> questions = readerFile.getQuestions();
        assertEquals(5, questions.size());
        assertEquals("2?", questions.get(1).getQuestionText());
    }

    @Test
    void assertBad() {
        ReaderFile readerFile = new ReaderFileCvsParserImpl("WrongFile.csv");
        assertThrows(AssertionError.class, readerFile::getQuestions);
    }
}