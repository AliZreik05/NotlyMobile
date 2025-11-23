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

    // TODO: replace with real logged-in user id
    private int userId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_quiz_activity);

        // ---- find views according to your XML ----
        searchLectureEditText = findViewById(R.id.searchLectureEditText);
        lecturesRecyclerView = findViewById(R.id.lecturesRecyclerView);
        generateQuizBar = findViewById(R.id.generateQuizBar);
        generateQuizText = findViewById(R.id.generateQuizText);
        quizBottomNav = findViewById(R.id.quizBottomNav);

        // ---- setup Retrofit (simple local instance) ----
        api = RetrofitAPI.getAuthApi();

        lecturesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadLectures();

        // ---- bottom nav ----
        QuizNavUtils.setupQuizBottomNav(quizBottomNav, this, R.id.nav_generate);

        // ---- generate quiz when bottom bar is tapped ----
        generateQuizBar.setOnClickListener(v -> onGenerateClicked());
        generateQuizText.setOnClickListener(v -> onGenerateClicked()); // tapping text also works
    }

    private void onGenerateClicked() {
        // For now we use the text typed in the search box as the quiz source.
        // Later you can change this to use the selected lecture's content.
        String text = searchLectureEditText.getText().toString().trim();

        if (text.isEmpty()) {
            Toast.makeText(this, "Enter or select some text first", Toast.LENGTH_SHORT).show();
            return;
        }

        int numQuestions = 10;  // fixed for now; you can change later

        QuizGenerationRequest request = new QuizGenerationRequest(text, numQuestions);

        generateQuizBar.setEnabled(false);
        generateQuizText.setText("Generating...");

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
                ArrayList<QuizQuestion> questions =
                        new ArrayList<>(body.getQuestions());

                // Go to ExamActivity with exam id + questions
                Intent intent = new Intent(GenerateQuizActivity.this, ExamActivity.class);
                intent.putExtra("exam_id", examId);
                intent.putExtra("exam_title", body.getTitle());
                intent.putExtra("questions", questions);  // QuizQuestion implements Serializable
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
        api.getLectures(userId).enqueue(new Callback<List<LectureResponse>>() {
            @Override
            public void onResponse(Call<List<LectureResponse>> call,
                                   Response<List<LectureResponse>> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(GenerateQuizActivity.this,
                            "Failed to load lectures", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convert backend → UI model
                ArrayList<Lecture> lectureList = new ArrayList<>();

                for (LectureResponse r : response.body()) {
                    lectureList.add(new Lecture(r.getId(), r.getTitle()));
                }

                LectureAdapter adapter = new LectureAdapter(lectureList);
                lecturesRecyclerView.setAdapter(adapter);
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
