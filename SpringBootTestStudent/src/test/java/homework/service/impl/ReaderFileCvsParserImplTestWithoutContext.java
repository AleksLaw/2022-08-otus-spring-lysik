package homework.service.impl;

import homework.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Сервис тестирования чтения файла без поднятия контекста")
@ExtendWith(MockitoExtension.class)
class ReaderFileCvsParserImplTestWithoutContext {
    List<Question> questions;

    @BeforeEach
    void setUp() {
        ReaderFileCvsParserImpl readerFileCvsParser = new ReaderFileCvsParserImpl();
        questions = readerFileCvsParser.getQuestions("Questions_ru.csv");
    }

    @DisplayName("количество вопросов")
    @Test
    void getQuestionsCount() {
        assertEquals(5, questions.size());
    }

    @DisplayName("проверка текста вопроса")
    @Test
    void getQuestionText() {
        assertEquals("Сколько у тебя пальцев?", questions.get(0).getQuestionText());
    }

    @DisplayName("количество ответов для вопроса")
    @Test
    void getAnswersCount() {
        assertEquals(5, questions.get(0).getListAnswers().size());
    }

    @DisplayName("текст ответов")
    @Test
    void getAnswersText() {
        assertEquals("1", questions.get(0).getListAnswers().get(0));
        assertEquals("20", questions.get(0).getListAnswers().get(1));
        assertEquals("3", questions.get(0).getListAnswers().get(2));
        assertEquals("4", questions.get(0).getListAnswers().get(3));
        assertEquals("10", questions.get(0).getListAnswers().get(4));
    }

    @DisplayName("правльный ответ")
    @Test
    void getCorrectAnswer() {
        assertEquals("20", questions.get(0).getCorrectAnswer());
    }

}