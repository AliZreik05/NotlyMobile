package com.example.notly;

public class QuestionResultDto {
    private int question_id;
    private int correct_index;
    private Integer user_index;
    private boolean is_correct;

    public int getQuestionId() { return question_id; }
    public int getCorrectIndex() { return correct_index; }
    public Integer getUserIndex() { return user_index; }
    public boolean getIsCorrect() { return is_correct; }
}
