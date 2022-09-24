package homework.service;

import homework.domain.Question;

import java.util.List;

public interface ReaderFile {
    List<Question> getQuestions(String filename);
}
