package com.example.notly;

public class Lecture {
    private int id;
    private String title;
    private boolean selected;

    public Lecture(int id, String title) {
        this.id = id;
        this.title = title;
        this.selected = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    // 🔹 NEW
    public String getInitial() {
        if (title != null && title.length() > 0) {
            return title.substring(0, 1).toUpperCase();
        }
        return "?";
    }
}
