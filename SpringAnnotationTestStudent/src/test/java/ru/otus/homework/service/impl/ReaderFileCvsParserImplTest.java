package ru.otus.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Сервис парсинга CSV")
@ExtendWith(MockitoExtension.class)
class ReaderFileCvsParserImplTest {
    private ReaderFileCvsParserImpl readerFileCvsParser;

    @BeforeEach
    void setUp() {
        readerFileCvsParser = new ReaderFileCvsParserImpl("Questions.csv");
    }

    @DisplayName("должен возвращать все вопросы из фaйла (проверка парсинга)")
    @Test
    void getQuestions() {
        List<Question> questions = readerFileCvsParser.getQuestions();
        assertEquals(5, questions.size());
        assertEquals("How many fingers do you have?", questions.get(0).getQuestionText());
        assertEquals(5, questions.get(0).getListAnswers().size());
        assertEquals("1", questions.get(0).getListAnswers().get(0));
        assertEquals("20", questions.get(0).getCorrectAnswer());
    }
}
