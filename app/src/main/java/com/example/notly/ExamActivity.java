package com.example.notly;  // <- change to your package

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamActivity extends AppCompatActivity {

    private RecyclerView questionsRecyclerView;
    private LinearLayout submitBar;
    private ExamQuestionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_activity);

        questionsRecyclerView = findViewById(R.id.questionsRecyclerView);
        submitBar = findViewById(R.id.submitBar);

        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data for now – later you’ll fill this from backend
        List<ExamQuestion> questions = buildDummyQuestions();

        adapter = new ExamQuestionAdapter(questions);
        questionsRecyclerView.setAdapter(adapter);

        submitBar.setOnClickListener(v -> handleSubmit());
    }

    private List<ExamQuestion> buildDummyQuestions() {
        List<ExamQuestion> list = new ArrayList<>();

        list.add(new ExamQuestion(
                "Question 1",
                "Question description",
                Arrays.asList("Option A", "Option B", "Option C")
        ));

        list.add(new ExamQuestion(
                "Question 2",
                "Question description",
                Arrays.asList("Choice 1", "Choice 2", "Choice 3")
        ));

        list.add(new ExamQuestion(
                "Question 3",
                "Question description",
                Arrays.asList("Answer 1", "Answer 2", "Answer 3")
        ));

        return list;
    }

    private void handleSubmit() {
        List<ExamQuestion> questions = adapter.getQuestions();
        int answered = 0;

        for (ExamQuestion q : questions) {
            if (q.getSelectedOptionIndex() != -1) {
                answered++;
            }
        }

        Toast.makeText(
                this,
                "You answered " + answered + " / " + questions.size() + " questions",
                Toast.LENGTH_SHORT
        ).show();

        // Later: send answers to backend here
    }
}
