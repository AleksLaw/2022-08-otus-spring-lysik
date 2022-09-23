package homework.service.impl;

import homework.config.AppProperties;
import homework.domain.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Сервис тестирования чтения файла с поднятием контекста")
@SpringBootTest
class ReaderFileCvsParserImplTest {
    @Autowired
    private AppProperties appProperties;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ReaderFileCvsParserImpl readerFileCvsParser;

    @DisplayName("количество вопросов")
    @Test
    void getQuestionsCount() {
        List<Question> questions = readerFileCvsParser.getQuestions();
        assertEquals(5, questions.size());
    }

    @DisplayName("проверка текста вопроса EN")
    @Test
    void getQuestionTextEN() {
        appProperties.setLocale(new Locale("en", "EN"));
        readerFileCvsParser = new ReaderFileCvsParserImpl(messageSource, appProperties);
        List<Question> questions = readerFileCvsParser.getQuestions();
        assertEquals("How many fingers do you have?", questions.get(0).getQuestionText());
    }

    @DisplayName("проверка текста вопроса RU")
    @Test
    void getQuestionTextRU() {
        appProperties.setLocale(new Locale("ru", "RU"));
        readerFileCvsParser = new ReaderFileCvsParserImpl(messageSource, appProperties);
        List<Question> questions = readerFileCvsParser.getQuestions();
        assertEquals("Сколько у тебя пальцев?", questions.get(0).getQuestionText());
    }

    @DisplayName("проверка текста вопроса BY")
    @Test
    void getQuestionTextBY() {
        appProperties.setLocale(new Locale("be", "BY"));
        readerFileCvsParser = new ReaderFileCvsParserImpl(messageSource, appProperties);
        List<Question> questions = readerFileCvsParser.getQuestions();
        assertEquals("Колькі ў цябе пальцаў?", questions.get(0).getQuestionText());
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