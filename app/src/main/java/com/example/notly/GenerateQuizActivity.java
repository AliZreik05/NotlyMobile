package com.example.notly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenerateQuizActivity extends AppCompatActivity {

    private EditText searchLectureEditText;
    private RecyclerView lecturesRecyclerView;
    private LinearLayout generateQuizBar;
    private TextView generateQuizText;
    private BottomNavigationView quizBottomNav;

    private AuthApi api;
    private LectureAdapter lectureAdapter;
    private ArrayList<Lecture> lectureList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_quiz_activity);
        int userId = getSharedPreferences("notly_prefs", MODE_PRIVATE)
                .getInt("user_id", -1);
        Toast.makeText(GenerateQuizActivity.this,
                String.valueOf(userId), Toast.LENGTH_SHORT).show();

        // ---- find views according to your XML ----
        searchLectureEditText = findViewById(R.id.searchLectureEditText);
        lecturesRecyclerView = findViewById(R.id.lecturesRecyclerView);
        generateQuizBar = findViewById(R.id.generateQuizBar);
        generateQuizText = findViewById(R.id.generateQuizText);
        quizBottomNav = findViewById(R.id.quizBottomNav);

        // ---- setup Retrofit (simple local instance) ----
        api = RetrofitAPI.getAuthApi();

        lecturesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        lectureAdapter = new LectureAdapter(lectureList);
        lecturesRecyclerView.setAdapter(lectureAdapter);
        loadLectures();

        // ---- bottom nav ----
        QuizNavUtils.setupQuizBottomNav(quizBottomNav, this, R.id.nav_generate);

        // ---- generate quiz when bottom bar is tapped ----
        generateQuizBar.setOnClickListener(v -> onGenerateClicked());
        generateQuizText.setOnClickListener(v -> onGenerateClicked()); // tapping text also works
    }

    private void onGenerateClicked() {
        String text = searchLectureEditText.getText().toString().trim();

        // 🔹 If user didn’t type anything, use selected lectures
        if (text.isEmpty()) {
            // collect selected lectures
            StringBuilder sb = new StringBuilder();
            for (Lecture lecture : lectureAdapter.getLectures()) {
                if (lecture.isSelected()) {
                    if (sb.length() > 0) sb.append("\n\n");
                    // For now we only have the title; later you can replace with full content
                    sb.append(lecture.getContent());
                }
            }
            text = sb.toString().trim();
        }

        if (text.isEmpty()) {
            Toast.makeText(this, "Enter or select some text first", Toast.LENGTH_SHORT).show();
            return;
        }

        int numQuestions = 10;  // fixed for now

        QuizGenerationRequest request = new QuizGenerationRequest(text, numQuestions);

        generateQuizBar.setEnabled(false);
        generateQuizText.setText("Generating...");
        int userId = getSharedPreferences("notly_prefs", MODE_PRIVATE)
                .getInt("user_id", -1);
        api.generateQuiz(userId, request).enqueue(new Callback<GenerateQuizResponse>() {
            @Override
            public void onResponse(Call<GenerateQuizResponse> call, Response<GenerateQuizResponse> response) {
                generateQuizBar.setEnabled(true);
                generateQuizText.setText("Generate Quiz");

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(GenerateQuizActivity.this,
                            "Failed to generate quiz", Toast.LENGTH_SHORT).show();
                    return;
                }

                GenerateQuizResponse body = response.body();

                int examId = body.getExamId();

                // 🔹 Convert backend QuizQuestion -> UI ExamQuestion
                ArrayList<ExamQuestion> examQuestions = new ArrayList<>();
                int index = 1;
                for (QuizQuestion q : body.getQuestions()) {
                    ExamQuestion eq = new ExamQuestion(
                            q.getId(),                 // ✅ REAL DB ID
                            "Question " + index++,     // title
                            q.getQuestion(),           // description
                            q.getOptions(),            // options
                            q.getCorrectIndex()        // correctOptionIndex
                    );
                    examQuestions.add(eq);
                }


                // 🔹 Start ExamActivity with what it EXPECTS
                Intent intent = new Intent(GenerateQuizActivity.this, ExamActivity.class);
                intent.putExtra("exam_id", examId);
                intent.putExtra("exam_title", body.getTitle());
                intent.putExtra("exam_questions", examQuestions);  // ✅ correct key + type
                startActivity(intent);
            }


            @Override
            public void onFailure(Call<GenerateQuizResponse> call, Throwable t) {
                generateQuizBar.setEnabled(true);
                generateQuizText.setText("Generate Quiz");
                Toast.makeText(GenerateQuizActivity.this,
                        "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadLectures() {
        int userId = getSharedPreferences("notly_prefs", MODE_PRIVATE)
                .getInt("user_id", -1);
        api.getLectures(userId).enqueue(new Callback<List<LectureResponse>>() {
            @Override
            public void onResponse(Call<List<LectureResponse>> call,
                                   Response<List<LectureResponse>> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(GenerateQuizActivity.this,
                            "Failed to load lectures", Toast.LENGTH_SHORT).show();
                    return;
                }

                lectureList.clear();
                for (LectureResponse r : response.body()) {
                    lectureList.add(new Lecture(
                            r.getId(),
                            r.getTitle(),
                            r.getContent()
                    ));

                }
                lectureAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<LectureResponse>> call, Throwable t) {
                Toast.makeText(GenerateQuizActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



}
