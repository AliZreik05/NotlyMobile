package com.example.notly;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GenerateQuizResponse {

    @SerializedName("exam_id")
    private int examId;

    private String title;

    private List<QuizQuestion> questions;

    public int getExamId() {
        return examId;
    }

    public String getTitle() {
        return title;
    }

    public List<QuizQuestion> getQuestions() {
        return questions;
    }
}
