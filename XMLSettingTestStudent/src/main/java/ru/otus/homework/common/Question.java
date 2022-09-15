package ru.otus.homework.common;

import java.util.List;

public class Question {
    private final String questionText;
    private final List<String> answers;
    private final String correctAnswer;

    public Question(String questionText, List<String> answers, String correctAnswer) {
        this.questionText = questionText;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
