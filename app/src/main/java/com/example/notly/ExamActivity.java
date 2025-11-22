package com.example.notly;  // <- change to your package

import android.content.Intent;
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
    private List<ExamQuestion> questions;

    private LinearLayout submitBar;
    private ExamQuestionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_activity);

        questionsRecyclerView = findViewById(R.id.questionsRecyclerView);
        submitBar = findViewById(R.id.submitBar);

        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        questionsRecyclerView = findViewById(R.id.questionsRecyclerView);
        submitBar = findViewById(R.id.submitBar);

        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        questions = (List<ExamQuestion>) getIntent().getSerializableExtra("exam_questions");
        if (questions == null || questions.isEmpty()) {
            // fallback if something went wrong
            questions = buildDummyQuestions();
        }

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
        int correct = 0;

        ArrayList<ExamResultQuestion> resultQuestions = new ArrayList<>();

        for (ExamQuestion q : questions) {
            int selected = q.getSelectedOptionIndex();
            int correctIdx = q.getCorrectOptionIndex();

            if (selected != -1) answered++;
            if (selected != -1 && selected == correctIdx) correct++;

            resultQuestions.add(new ExamResultQuestion(
                    q.getTitle(),
                    q.getDescription(),
                    q.getOptions(),
                    correctIdx,
                    selected
            ));
        }

        Intent intent = new Intent(this, ExamResultActivity.class);
        intent.putExtra("result_questions", resultQuestions);
        startActivity(intent);
    }

}
