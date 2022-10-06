package homework.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class Question {
    private final String questionText;
    private final List<String> listAnswers;
    private final String correctAnswer;
}
