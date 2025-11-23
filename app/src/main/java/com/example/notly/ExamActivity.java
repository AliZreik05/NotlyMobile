package com.example.notly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamActivity extends AppCompatActivity {

    private RecyclerView questionsRecyclerView;
    private LinearLayout submitBar;
    private ExamQuestionAdapter adapter;
    private List<ExamQuestion> questions;

    private AuthApi api;
    private int userId = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_activity);

        api = RetrofitAPI.getAuthApi();

        questionsRecyclerView = findViewById(R.id.questionsRecyclerView);
        submitBar = findViewById(R.id.submitBar);
        submitBar.setOnClickListener(v -> submitExam());

        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        questions = (List<ExamQuestion>)
                getIntent().getSerializableExtra("exam_questions");

        adapter = new ExamQuestionAdapter(questions);
        questionsRecyclerView.setAdapter(adapter);

        submitBar.setOnClickListener(v -> submitExam());
    }

    private void submitExam() {
        int examId = getIntent().getIntExtra("exam_id", -1);

        Map<Integer, Integer> answersMap = adapter.getSelectedAnswers();

        GradeRequestDto body = new GradeRequestDto(answersMap);

        api.gradeExam(examId, userId, body).enqueue(new Callback<GradeResultDto>() {
            @Override
            public void onResponse(Call<GradeResultDto> call, Response<GradeResultDto> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(ExamActivity.this, "Failed to grade", Toast.LENGTH_SHORT).show();
                    return;
                }

                GradeResultDto result = response.body();

                Intent intent = new Intent(ExamActivity.this, ExamResultActivity.class);
                intent.putExtra("from_history", false);
                intent.putExtra("exam_id", examId);
                intent.putExtra("result_questions", convertToUiModel(result));
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<GradeResultDto> call, Throwable t) {
                Toast.makeText(ExamActivity.this, "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<ExamResultQuestion> convertToUiModel(GradeResultDto dto) {
        ArrayList<ExamResultQuestion> list = new ArrayList<>();

        for (QuestionResultDto d : dto.getDetails()) {
            ExamQuestion backendQ = null;

            for (ExamQuestion q : questions) {
                if (q.getId() == d.getQuestionId()) {
                    backendQ = q;
                    break;
                }
            }
            if (backendQ == null) continue;

            list.add(new ExamResultQuestion(
                    backendQ.getTitle(),
                    backendQ.getDescription(),
                    backendQ.getOptions(),
                    d.getCorrectIndex(),
                    d.getUserIndex() != null ? d.getUserIndex() : -1
            ));
        }

        return list;
    }
}
