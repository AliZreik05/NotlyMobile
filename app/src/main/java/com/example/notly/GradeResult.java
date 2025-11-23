package com.example.notly;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GradeResult {

    @SerializedName("exam_id")
    private int examId;

    @SerializedName("total_questions")
    private int totalQuestions;

    private int correct;
    private int grade;

    private List<QuestionResult> details;

    public int getExamId() {
        return examId;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrect() {
        return correct;
    }

    public int getGrade() {
        return grade;
    }

    public List<QuestionResult> getDetails() {
        return details;
    }
}
