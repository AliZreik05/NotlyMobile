package com.example.notly;

import java.io.Serializable;
import java.util.List;

public class ExamResultQuestion implements Serializable {

    private String title;
    private String description;
    private List<String> options;

    private int correctIndex;
    private int selectedIndex;

    public ExamResultQuestion(String title,
                              String description,
                              List<String> options,
                              int correctIndex,
                              int selectedIndex) {
        this.title = title;
        this.description = description;
        this.options = options;
        this.correctIndex = correctIndex;
        this.selectedIndex = selectedIndex;
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

    public int getCorrectIndex() {
        return correctIndex;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}
