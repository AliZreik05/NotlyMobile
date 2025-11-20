package com.example.notly;

public class Lecture {
    private String title;
    private boolean selected = true;

    public Lecture(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getInitial() {
        if (title != null && !title.isEmpty()) {
            return title.substring(0, 1).toUpperCase();
        }
        return "A";
    }
}
