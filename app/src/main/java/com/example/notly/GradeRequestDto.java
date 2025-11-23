package com.example.notly;

import java.util.Map;

public class GradeRequestDto {
    private Map<Integer, Integer> answers;

    public GradeRequestDto(Map<Integer, Integer> answers) {
        this.answers = answers;
    }

    public Map<Integer, Integer> getAnswers() {
        return answers;
    }
}
