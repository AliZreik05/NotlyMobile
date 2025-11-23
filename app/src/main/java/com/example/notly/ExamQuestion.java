package com.example.notly;

import java.io.Serializable;
import java.util.List;

public class ExamQuestion implements Serializable {

    // 🔹 NEW: backend question id (ExamQuestion.id in DB)
    private int id;

    private String title;                 // e.g. "Question 1"
    private String description;           // the actual question text
    private List<String> options;         // choices shown to user

    // Index of the correct answer (0..3), -1 if unknown
    private int correctOptionIndex = -1;

    // Index selected by the user (0..3), -1 = not answered yet
    private int selectedOptionIndex = -1;

    // --- Constructors ---

    // Simple constructor (e.g. for dummy questions)
    public ExamQuestion(String title,
                        String description,
                        List<String> options) {
        this.title = title;
        this.description = description;
        this.options = options;
    }

    // 🔹 Preferred constructor: includes backend id + correct index
    public ExamQuestion(int id,
                        String title,
                        String description,
                        List<String> options,
                        int correctOptionIndex) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
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


    // --- Getters ---

    public int getId() {
        return id;
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

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public int getSelectedOptionIndex() {
        return selectedOptionIndex;
    }

    // --- Setters ---

    public void setId(int id) {
        this.id = id;
    }

    public void setSelectedOptionIndex(int selectedOptionIndex) {
        this.selectedOptionIndex = selectedOptionIndex;
    }

    public void setCorrectOptionIndex(int correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }
}
