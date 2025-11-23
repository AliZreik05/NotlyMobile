package com.example.notly;

import java.io.Serializable;
import java.util.List;

public class ExamQuestion implements Serializable {

    private String title;                 // e.g. "Question 1"
    private String description;           // the actual question text
    private List<String> options;         // choices shown to user

    // Index of the correct answer (0..2), -1 if unknown (for backend grading)
    private int correctOptionIndex = -1;

    // Index selected by the user (0..2), -1 = not answered yet
    private int selectedOptionIndex = -1;

    public ExamQuestion(String title,
                        String description,
                        List<String> options) {
        this.title = title;
        this.description = description;
        this.options = options;
    }

    public ExamQuestion(String title,
                        String description,
                        List<String> options,
                        int correctOptionIndex) {
        this.title = title;
        this.description = description;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    // Getters

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public int getSelectedOptionIndex() {
        return selectedOptionIndex;
    }

    // Setters

    public void setSelectedOptionIndex(int selectedOptionIndex) {
        this.selectedOptionIndex = selectedOptionIndex;
    }

    public void setCorrectOptionIndex(int correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }
}
