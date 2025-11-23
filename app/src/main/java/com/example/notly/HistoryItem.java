package com.example.notly;

import com.google.gson.annotations.SerializedName;

public class HistoryItem {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("grade")
    private double grade;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public double getGrade() {
        return grade;
    }
}
