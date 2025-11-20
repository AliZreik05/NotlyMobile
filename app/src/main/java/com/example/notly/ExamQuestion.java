package com.example.notly;  // <- change to your package

import java.util.List;

public class ExamQuestion {

    private String title;
    private String description;
    private List<String> options;
    // -1 means nothing selected yet
    private int selectedOptionIndex = -1;

    public ExamQuestion(String title, String description, List<String> options) {
        this.title = title;
        this.description = description;
        this.options = options;
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
}
