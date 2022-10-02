package homework.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@RequiredArgsConstructor
@Getter
@Setter
public class TestResult {
    private final Student student;
    private boolean result;
    private int score;
    private ArrayList<String> wrongAnswer;
}
