package com.example.notly;

import java.util.List;

public class ExamQuestionItem {

    private int id;
    private String question;
    private List<String> options;
    private Integer order; // can be null

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public Integer getOrder() {
        return order;
    }
}
