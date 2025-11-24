package com.example.notly;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamResultActivity extends AppCompatActivity {

    private RecyclerView resultsRecyclerView;
    private ResultQuestionAdapter adapter;
    private TextView scoreObtainedTextView;
    private TextView scoreTotalTextView;
    private LinearLayout returnBar;

    private AuthApi api;
    private int userId = 1;  // TODO: replace with real logged-in user id if needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_result_activity);

        api = RetrofitAPI.getAuthApi();

        // Match your XML ids
        resultsRecyclerView = findViewById(R.id.resultsRecyclerView);
        scoreObtainedTextView = findViewById(R.id.scoreObtainedTextView);
        scoreTotalTextView = findViewById(R.id.scoreTotalTextView);
        returnBar = findViewById(R.id.returnBar);
        BottomNavigationView quizBottomNav = findViewById(R.id.quizBottomNav);
        QuizNavUtils.setupQuizBottomNav(quizBottomNav, this, R.id.nav_quiz_history);


        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tap on "Return to home" -> close screen
        returnBar.setOnClickListener(v -> finish());

        int examId = getIntent().getIntExtra("exam_id", -1);

        @SuppressWarnings("unchecked")
        ArrayList<ExamResultQuestion> localResult =
                (ArrayList<ExamResultQuestion>)
                        getIntent().getSerializableExtra("result_questions");

        // 1) If we got local result directly (freshly graded), use it
        if (localResult != null && !localResult.isEmpty()) {
            showResult(localResult);
        }
        // 2) Otherwise, if we at least know the examId, load from backend
        else if (examId != -1) {
            loadResultFromBackend(examId);
        }
        // 3) Only then give up
        else {
            Toast.makeText(this, "No result data", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Common function to display result in the UI.
     */
    private void showResult(List<ExamResultQuestion> questions) {
        int total = questions.size();
        int correct = 0;

        for (ExamResultQuestion q : questions) {
            if (q.getSelectedIndex() == q.getCorrectIndex()) {
                correct++;
            }
        }

        scoreObtainedTextView.setText(String.valueOf(correct));
        scoreTotalTextView.setText(String.valueOf(total));

        adapter = new ResultQuestionAdapter(questions);
        resultsRecyclerView.setAdapter(adapter);
    }

    /**
     * Called when we open from History OR when localResult was missing.
     * 1) Fetch exam with questions (/exam/{id})
     * 2) Fetch stored result (/exam/{id}/result)
     * 3) Merge both into ExamResultQuestion list and call showResult()
     */
    private void loadResultFromBackend(int examId) {
        // 1) Fetch exam with questions
        api.getExam(examId).enqueue(new Callback<ExamDetail>() {
            @Override
            public void onResponse(Call<ExamDetail> call, Response<ExamDetail> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(ExamResultActivity.this,
                            "Failed to load exam", Toast.LENGTH_SHORT).show();
                    try {
                        if (response.errorBody() != null) {
                            android.util.Log.e("EXAM", "errorBody: " + response.errorBody().string());
                        }
                    } catch (Exception ignored) {}
                    finish();
                    return;
                }

                ExamDetail exam = response.body();
                List<ExamQuestionItem> questionItems = exam.getQuestions();

                Map<Integer, ExamQuestionItem> questionMap = new HashMap<>();
                for (ExamQuestionItem q : questionItems) {
                    questionMap.put(q.getId(), q);
                }

                // 2) Fetch stored result
                api.getExamResult(examId).enqueue(new Callback<GradeResultDto>() {
                    @Override
                    public void onResponse(Call<GradeResultDto> call,
                                           Response<GradeResultDto> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Toast.makeText(ExamResultActivity.this,
                                    "Failed to load result", Toast.LENGTH_SHORT).show();
                            try {
                                if (response.errorBody() != null) {
                                    android.util.Log.e("EXAM_RES", "errorBody: " + response.errorBody().string());
                                }
                            } catch (Exception ignored) {}
                            finish();
                            return;
                        }

                        GradeResultDto resultDto = response.body();

                        int correct = resultDto.getCorrect();
                        int total = resultDto.getTotalQuestions();
                        scoreObtainedTextView.setText(String.valueOf(correct));
                        scoreTotalTextView.setText(String.valueOf(total));

                        ArrayList<ExamResultQuestion> uiList = new ArrayList<>();

                        for (QuestionResultDto d : resultDto.getDetails()) {
                            ExamQuestionItem qi = questionMap.get(d.getQuestionId());
                            if (qi == null) continue;

                            uiList.add(new ExamResultQuestion(
                                    // title
                                    "Question " + (qi.getOrder() != null
                                            ? qi.getOrder()
                                            : uiList.size() + 1),
                                    // description
                                    qi.getQuestion(),
                                    // options
                                    qi.getOptions(),
                                    // correct index
                                    d.getCorrectIndex(),
                                    // selected index (may be null)
                                    d.getUserIndex() != null ? d.getUserIndex() : -1
                            ));
                        }

                        showResult(uiList);
                    }

                    @Override
                    public void onFailure(Call<GradeResultDto> call, Throwable t) {
                        Toast.makeText(ExamResultActivity.this,
                                "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(Call<ExamDetail> call, Throwable t) {
                Toast.makeText(ExamResultActivity.this,
                        "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
