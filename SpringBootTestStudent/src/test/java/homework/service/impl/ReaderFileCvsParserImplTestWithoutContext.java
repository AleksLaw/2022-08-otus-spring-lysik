package homework.service.impl;

import homework.config.AppProperties;
import homework.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("Сервис тестирования чтения файла без поднятия контекста")
@ExtendWith(MockitoExtension.class)
class ReaderFileCvsParserImplTestWithoutContext {
    private ReaderFileCvsParserImpl readerFileCvsParser;
    @Mock
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Questions_ru.csv");
        readerFileCvsParser = new ReaderFileCvsParserImpl(messageSource, new AppProperties());
    }

    @DisplayName("количество вопросов")
    @Test
    void getQuestionsCount() {
        List<Question> questions = readerFileCvsParser.getQuestions();
        assertEquals(5, questions.size());
    }

    @DisplayName("проверка текста вопроса")
    @Test
    void getQuestionText() {
        List<Question> questions = readerFileCvsParser.getQuestions();
        assertEquals("Сколько у тебя пальцев?", questions.get(0).getQuestionText());
    }

    @DisplayName("количество ответов для вопроса")
    @Test
    void getAnswersCount() {
        List<Question> questions = readerFileCvsParser.getQuestions();
        assertEquals(5, questions.get(0).getListAnswers().size());
    }

    @DisplayName("текст ответов")
    @Test
    void getAnswersText() {
        List<Question> questions = readerFileCvsParser.getQuestions();
        assertEquals("1", questions.get(0).getListAnswers().get(0));
        assertEquals("20", questions.get(0).getListAnswers().get(1));
        assertEquals("3", questions.get(0).getListAnswers().get(2));
        assertEquals("4", questions.get(0).getListAnswers().get(3));
        assertEquals("10", questions.get(0).getListAnswers().get(4));
    }

    @DisplayName("правльный ответ")
    @Test
    void getCorrectAnswer() {
        List<Question> questions = readerFileCvsParser.getQuestions();
        assertEquals("20", questions.get(0).getCorrectAnswer());
    }

}