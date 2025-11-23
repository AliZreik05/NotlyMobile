package com.example.notly;

import com.google.gson.annotations.SerializedName;

public class QuestionResult {

    @SerializedName("question_id")
    private int questionId;

    @SerializedName("correct_index")
    private int correctIndex;

    @SerializedName("user_index")
    private Integer userIndex;

    @SerializedName("is_correct")
    private boolean isCorrect;

    public int getQuestionId() {
        return questionId;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public Integer getUserIndex() {
        return userIndex;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
