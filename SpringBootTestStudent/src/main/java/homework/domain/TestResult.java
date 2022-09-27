package homework.domain;


import java.util.ArrayList;

public class TestResult {
    private final Student student;
    private boolean result;
    private int score;
    private ArrayList<String> wrongAnswer;

    public ArrayList<String> getWrongAnswer() {
        return wrongAnswer;
    }

    public void setWrongAnswer(ArrayList<String> wrongAnswer) {
        this.wrongAnswer = wrongAnswer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isResult() {
        return result;
    }

    public Student getStudent() {
        return student;
    }

    public TestResult(Student student) {
        this.student = student;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
