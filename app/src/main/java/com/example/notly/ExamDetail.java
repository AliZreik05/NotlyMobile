package com.example.notly;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ExamDetail {

    private int id;
    private String title;

    @SerializedName("created_at")
    private String createdAt;

    private Integer grade;

    @SerializedName("source_type")
    private String sourceType;

    @SerializedName("source_id")
    private Integer sourceId;

    private List<ExamQuestionItem> questions;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getSourceType() {
        return sourceType;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public List<ExamQuestionItem> getQuestions() {
        return questions;
    }
}
