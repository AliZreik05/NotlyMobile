package com.example.notly;

import java.util.List;

public class GradeResultDto {
    private int exam_id;
    private int total_questions;
    private int correct;
    private int grade;
    private List<QuestionResultDto> details;

    public int getExamId() { return exam_id; }
    public int getTotalQuestions() { return total_questions; }
    public int getCorrect() { return correct; }
    public int getGrade() { return grade; }
    public List<QuestionResultDto> getDetails() { return details; }
}
