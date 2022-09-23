package ru.otus.homework.domain;

import java.util.List;

public class Question {
    private final String questionText;
    private final List<String> listAnswers;
    private final String correctAnswer;

    public Question(String questionText, List<String> listAnswers, String correctAnswer) {
        this.questionText = questionText;
        this.listAnswers = listAnswers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getListAnswers() {
        return listAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
