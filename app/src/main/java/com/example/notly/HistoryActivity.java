package com.example.notly;

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
        AuthApi api = RetrofitAPI.getClient().create(AuthApi.class);

        int userId = 1; // TODO: replace with real stored user id

        api.getHistory(userId).enqueue(new Callback<List<ExamHistoryResponse>>() {
            @Override
            public void onResponse(Call<List<ExamHistoryResponse>> call,
                                   Response<List<ExamHistoryResponse>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(HistoryActivity.this,
                            "Failed to load history",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                List<QuizHistory> list = new ArrayList<>();

                for (ExamHistoryResponse e : response.body()) {
                    String date = e.getCreated_at().substring(0, 10); // YYYY-MM-DD
                    String score = "Score: " + e.getGrade() + " / 10 • " + date;

                    list.add(new QuizHistory(
                            e.getTitle(),
                            e.getTitle().substring(0, 1).toUpperCase(), // avatar initial
                            score,
                            e.getGrade() >= 6 // passed if grade >= 6
                    ));
                }

                QuizHistoryAdapter adapter = new QuizHistoryAdapter(list, quiz -> {
                    // Later: open ExamResultActivity for this quiz (using quiz id if you add it)
                });
                historyRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ExamHistoryResponse>> call, Throwable t) {
                Toast.makeText(HistoryActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
