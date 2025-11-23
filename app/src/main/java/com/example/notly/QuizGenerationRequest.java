package com.example.notly;

import com.google.gson.annotations.SerializedName;

public class QuizGenerationRequest {

    private String text;

    @SerializedName("num_questions")
    private int numQuestions;

    public QuizGenerationRequest(String text, int numQuestions) {
        this.text = text;
        this.numQuestions = numQuestions;
    }

    public String getText() {
        return text;
    }

    public int getNumQuestions() {
        return numQuestions;
    }
}
