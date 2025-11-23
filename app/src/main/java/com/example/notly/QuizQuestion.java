package com.example.notly;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class QuizQuestion implements Serializable {

    private int id;
    private String question;
    private List<String> options;

    @SerializedName("correct_index")
    private int correctIndex;

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }
}
