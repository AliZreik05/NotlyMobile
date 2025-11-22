package com.example.notly;

import android.content.Intent;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateQuizActivity extends AppCompatActivity {

    private RecyclerView lecturesRecyclerView;
    private EditText searchLectureEditText;

    private LectureAdapter adapter;
    private final List<Lecture> allLectures = new ArrayList<>();
    private final List<Lecture> filteredLectures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_quiz_activity);

        lecturesRecyclerView = findViewById(R.id.lecturesRecyclerView);
        searchLectureEditText = findViewById(R.id.searchLectureEditText);

        // RecyclerView first
        lecturesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LectureAdapter(filteredLectures);
        lecturesRecyclerView.setAdapter(adapter);

        setupSearch();

        // 🔹 NEW: get real lectures from backend instead of initData()
        fetchLecturesFromBackend();

        findViewById(R.id.generateQuizBar).setOnClickListener(v -> {
            List<Lecture> selected = new ArrayList<>();
            for (Lecture lec : allLectures) {
                if (lec.isSelected()) selected.add(lec);
            }

            if (selected.isEmpty()) {
                Toast.makeText(this, "Select at least one lecture", Toast.LENGTH_SHORT).show();
            } else {
                callQuizBackend(selected);
            }
        });

        // Setup quiz bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.quizBottomNav);
        QuizNavUtils.setupQuizBottomNav(bottomNav, this, R.id.nav_generate);
    }

    // 🔹 NEW: load lectures from FastAPI /lectures/
    private void fetchLecturesFromBackend() {
        AuthApi api = RetrofitAPI.getClient().create(AuthApi.class);

        // TODO: get real user id from SharedPreferences after login
        int userId = 1;

        api.getLectures(userId).enqueue(new Callback<List<LectureResponse>>() {
            @Override
            public void onResponse(Call<List<LectureResponse>> call,
                                   Response<List<LectureResponse>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(GenerateQuizActivity.this,
                            "Failed to load lectures (" + response.code() + ")",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                allLectures.clear();
                for (LectureResponse lr : response.body()) {
                    allLectures.add(new Lecture(lr.getId(), lr.getTitle()));
                }

                filteredLectures.clear();
                filteredLectures.addAll(allLectures);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<LectureResponse>> call, Throwable t) {
                Toast.makeText(GenerateQuizActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callQuizBackend(List<Lecture> selectedLectures) {
        StringBuilder topicBuilder = new StringBuilder();
        for (Lecture lec : selectedLectures) {
            if (topicBuilder.length() > 0) topicBuilder.append(", ");
            topicBuilder.append(lec.getTitle());
        }
        String topic = topicBuilder.toString();

        int numQuestions = 10;
        String difficulty = "medium";

        AuthApi api = RetrofitAPI.getClient().create(AuthApi.class);
        QuizRequest request = new QuizRequest(topic, null, numQuestions, difficulty);

        Toast.makeText(this, "Generating quiz…", Toast.LENGTH_SHORT).show();

        api.createQuiz(request).enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(GenerateQuizActivity.this,
                            "Failed to generate quiz (" + response.code() + ")",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                QuizResponse qr = response.body();
                List<QuizItem> items = qr.getItems();
                if (items == null || items.isEmpty()) {
                    Toast.makeText(GenerateQuizActivity.this,
                            "No questions returned from server",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<ExamQuestion> examQuestions = new ArrayList<>();
                int idx = 1;
                for (QuizItem item : items) {
                    ExamQuestion q = new ExamQuestion(
                            "Question " + idx,
                            item.getQuestion(),
                            item.getOptions(),
                            item.getAnswer_index()
                    );
                    examQuestions.add(q);
                    idx++;
                }

                Intent intent = new Intent(GenerateQuizActivity.this, ExamActivity.class);
                intent.putExtra("exam_questions", examQuestions);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                Toast.makeText(GenerateQuizActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
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
