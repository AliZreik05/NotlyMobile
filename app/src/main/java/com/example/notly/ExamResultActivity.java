package com.example.notly; // use your package

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamResultActivity extends AppCompatActivity {

    private RecyclerView resultsRecyclerView;
    private LinearLayout returnBar;
    private TextView scoreObtainedTextView;
    private TextView scoreTotalTextView;

    private ExamResultQuestionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_result_activity);

        resultsRecyclerView = findViewById(R.id.resultsRecyclerView);
        returnBar = findViewById(R.id.returnBar);
        scoreObtainedTextView = findViewById(R.id.scoreObtainedTextView);
        scoreTotalTextView = findViewById(R.id.scoreTotalTextView);

        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: later, get this from Intent extras
        List<ExamResultQuestion> questions = buildDummyResults();

        adapter = new ExamResultQuestionAdapter(questions);
        resultsRecyclerView.setAdapter(adapter);

        // compute score from data
        int total = questions.size();
        int correct = 0;
        for (ExamResultQuestion q : questions) {
            if (q.getSelectedIndex() == q.getCorrectIndex()) {
                correct++;
            }
        }

        scoreObtainedTextView.setText(String.valueOf(correct));
        scoreTotalTextView.setText(String.valueOf(total));

        // Return to home
        returnBar.setOnClickListener(v -> {
            // For now: finish and go back, or open your HomeActivity
            finish();
            // or:
            // startActivity(new Intent(this, HomeActivity.class));
        });
    }

    private List<ExamResultQuestion> buildDummyResults() {
        List<ExamResultQuestion> list = new ArrayList<>();

        list.add(new ExamResultQuestion(
                "Question 1",
                "Question description",
                Arrays.asList("Option A", "Option B", "Option C"),
                0,  // correct: A
                0   // user chose A (green)
        ));

        list.add(new ExamResultQuestion(
                "Question 2",
                "Question description",
                Arrays.asList("Choice 1", "Choice 2", "Choice 3"),
                2,  // correct: 3
                0   // user chose 1 (red, 3 will be green)
        ));

        list.add(new ExamResultQuestion(
                "Question 3",
                "Question description",
                Arrays.asList("Answer 1", "Answer 2", "Answer 3"),
                1,  // correct: 2
                1   // user chose 2 (green)
        ));

        return list;
    }
}
