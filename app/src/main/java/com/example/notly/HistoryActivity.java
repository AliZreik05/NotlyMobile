package com.example.notly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView historyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 🔹 Load real history from backend
        loadHistory();

        BottomNavigationView bottomNav = findViewById(R.id.quizBottomNav);
        QuizNavUtils.setupQuizBottomNav(bottomNav, this, R.id.nav_quiz_history);
    }

    private void loadHistory() {
        AuthApi api = RetrofitAPI.getAuthApi();

        int userId = 1; // TODO: replace with real stored user id

        api.getHistory(userId).enqueue(new Callback<List<HistoryItem>>() {
            @Override
            public void onResponse(Call<List<HistoryItem>> call,
                                   Response<List<HistoryItem>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(HistoryActivity.this,
                            "Failed to load history",
                            Toast.LENGTH_SHORT).show();
                    try {
                        if (response.errorBody() != null) {
                            android.util.Log.e("HISTORY", "errorBody: " + response.errorBody().string());
                        }
                    } catch (Exception ignored) {}
                    return;
                }

                List<QuizHistory> list = new ArrayList<>();

                for (HistoryItem e : response.body()) {

                    String date = "";
                    if (e.getCreated_at() != null && e.getCreated_at().length() >= 10) {
                        date = e.getCreated_at().substring(0, 10); // YYYY-MM-DD
                    }

                    String score = "Score: " + e.getGrade() + " / 10 • " + date;

                    String title = e.getTitle() != null ? e.getTitle() : "Exam";
                    String initial = title.isEmpty()
                            ? "?"
                            : title.substring(0, 1).toUpperCase();

                    list.add(new QuizHistory(
                            e.getId(),          // 👈 store backend id
                            title,
                            initial,
                            score,
                            e.getGrade() >= 6
                    ));

                }

                QuizHistoryAdapter adapter = new QuizHistoryAdapter(list, quiz -> {
                    Intent intent = new Intent(HistoryActivity.this, ExamResultActivity.class);
                    intent.putExtra("exam_id", quiz.getId());
                    intent.putExtra("exam_title", quiz.getTitle());
                    intent.putExtra("from_history", true);
                    startActivity(intent);
                });

                historyRecyclerView.setAdapter(adapter);

                historyRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<HistoryItem>> call, Throwable t) {
                Toast.makeText(HistoryActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
