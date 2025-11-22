package com.example.notly;

import java.util.List;

public class QuizItem {
    private String question;
    private List<String> options;
    private int answer_index;
    private String explanation;

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getAnswer_index() {
        return answer_index;
    }

    public String getExplanation() {
        return explanation;
    }
}
