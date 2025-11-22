package com.example.notly;  // your package

import java.io.Serializable;
import java.util.List;

public class ExamQuestion implements Serializable {

    private String title;
    private String description;
    private List<String> options;

    // -1 means nothing selected yet
    private int selectedOptionIndex = -1;

    // NEW: index of correct option (0,1,2,…)
    private int correctOptionIndex = -1;

    public ExamQuestion(String title, String description, List<String> options) {
        this.title = title;
        this.description = description;
        this.options = options;
    }

    public ExamQuestion(String title, String description, List<String> options, int correctOptionIndex) {
        this.title = title;
        this.description = description;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getSelectedOptionIndex() {
        return selectedOptionIndex;
    }

    public void setSelectedOptionIndex(int selectedOptionIndex) {
        this.selectedOptionIndex = selectedOptionIndex;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public void setCorrectOptionIndex(int correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }
}
