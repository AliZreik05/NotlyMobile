package com.example.notly;

import java.util.List;

public class QuizResponse {
    private Integer quiz_id;
    private List<QuizItem> items;
    private String message;

    public Integer getQuiz_id() {
        return quiz_id;
    }

    public List<QuizItem> getItems() {
        return items;
    }

    public String getMessage() {
        return message;
    }
}
