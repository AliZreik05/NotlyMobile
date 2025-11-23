package com.example.notly; // change to your package

public class QuizHistory {

    private int id;              // 👈 from backend HistoryItem.id
    private String title;
    private String courseInitial;
    private String subtitle;
    private boolean passed;

    public QuizHistory(int id, String title, String courseInitial,
                       String subtitle, boolean passed) {
        this.id = id;
        this.title = title;
        this.courseInitial = courseInitial;
        this.subtitle = subtitle;
        this.passed = passed;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCourseInitial() { return courseInitial; }
    public String getSubtitle() { return subtitle; }
    public boolean isPassed() { return passed; }
}
