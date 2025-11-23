package com.example.notly;

import java.util.Map;

public class GradeRequest {

    // questionId -> selectedIndex
    private Map<Integer, Integer> answers;

    public GradeRequest(Map<Integer, Integer> answers) {
        this.answers = answers;
    }

    public Map<Integer, Integer> getAnswers() {
        return answers;
    }
}
