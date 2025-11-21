package com.example.notly;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView historyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<QuizHistory> items = buildDummyHistory();

        QuizHistoryAdapter adapter = new QuizHistoryAdapter(items, quiz -> {
            // Later: open ExamResultActivity for this quiz
        });

        historyRecyclerView.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.quizBottomNav);
        QuizNavUtils.setupQuizBottomNav(bottomNav, this, R.id.nav_quiz_history);
    }

    private List<QuizHistory> buildDummyHistory() {
        List<QuizHistory> list = new ArrayList<>();
        list.add(new QuizHistory(
                "Biology – Week 3 Quiz",
                "B",
                "Score: 8 / 10 • Nov 18, 2025",
                true));

        list.add(new QuizHistory(
                "Organic Chemistry – Midterm Review",
                "O",
                "Score: 5 / 10 • Nov 10, 2025",
                false));

        list.add(new QuizHistory(
                "Operating Systems – Scheduling Quiz",
                "OS",
                "Score: 9 / 10 • Nov 5, 2025",
                true));

        return list;
    }
}
