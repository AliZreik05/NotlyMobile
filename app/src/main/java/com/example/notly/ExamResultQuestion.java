package com.example.notly; // use your package

import java.util.List;

public class ExamResultQuestion {

    private String title;
    private String description;
    private List<String> options;

    // index of correct option (0,1,2)
    private int correctIndex;

    // index the user selected (-1 if unanswered)
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
