package com.example.notly; // change to your package

public class QuizHistory {

    private String title;
    private String courseInitial;  // for avatar: e.g., "B"
    private String subtitle;       // "Score: 8 / 10 • Nov 20, 2025"
    private boolean passed;

    public QuizHistory(String title, String courseInitial, String subtitle, boolean passed) {
        this.title = title;
        this.courseInitial = courseInitial;
        this.subtitle = subtitle;
        this.passed = passed;
    }

    public String getTitle() {
        return title;
    }

    public String getCourseInitial() {
        return courseInitial;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public boolean isPassed() {
        return passed;
    }
}
