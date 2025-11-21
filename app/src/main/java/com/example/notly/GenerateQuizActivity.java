package com.example.notly;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class GenerateQuizActivity extends AppCompatActivity {

    private RecyclerView lecturesRecyclerView;
    private EditText searchLectureEditText;

    private LectureAdapter adapter;
    private List<Lecture> allLectures = new ArrayList<>();
    private List<Lecture> filteredLectures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_quiz_activity);

        lecturesRecyclerView = findViewById(R.id.lecturesRecyclerView);
        searchLectureEditText = findViewById(R.id.searchLectureEditText);

        initData();
        setupRecyclerView();
        setupSearch();

        findViewById(R.id.generateQuizBar).setOnClickListener(v -> {
            int count = 0;
            for (Lecture lec : allLectures) {
                if (lec.isSelected()) count++;
            }
            if (count == 0) {
                Toast.makeText(this, "Select at least one lecture", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "Generate quiz from " + count + " lecture(s)",
                        Toast.LENGTH_SHORT).show();
                // Later: start ExamActivity
            }
        });

        // Setup quiz bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.quizBottomNav);
        QuizNavUtils.setupQuizBottomNav(bottomNav, this, R.id.nav_generate);
    }

    private void initData() {
        for (int i = 1; i <= 12; i++) {
            allLectures.add(new Lecture("Lecture " + i));
        }
        filteredLectures.addAll(allLectures);
    }

    private void setupRecyclerView() {
        lecturesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LectureAdapter(filteredLectures);
        lecturesRecyclerView.setAdapter(adapter);
    }

    private void setupSearch() {
        searchLectureEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override public void afterTextChanged(Editable s) { }
        });
    }

    private void filter(String text) {
        filteredLectures.clear();
        String lower = text.toLowerCase();

        for (Lecture lecture : allLectures) {
            if (lecture.getTitle().toLowerCase().contains(lower)) {
                filteredLectures.add(lecture);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
