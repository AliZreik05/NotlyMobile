package com.example.notly;

public class QuizRequest {
    private String topic;
    private String source_text;
    private int num_questions;
    private String difficulty; // "easy" | "medium" | "hard"

    public QuizRequest(String topic, String source_text, int num_questions, String difficulty) {
        this.topic = topic;
        this.source_text = source_text;
        this.num_questions = num_questions;
        this.difficulty = difficulty;
    }
}
