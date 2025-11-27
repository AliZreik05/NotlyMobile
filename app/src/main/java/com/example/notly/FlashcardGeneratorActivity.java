package com.example.notly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlashcardGeneratorActivity extends AppCompatActivity {

    private EditText etFlashNotes, etFlashCount;
    private Button btnFlashBrowse, btnFlashGenerate;
    private TextView tvFlashResultTitle;
    private LinearLayout flashcardListContainer;
    private AuthApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_generator);

        etFlashNotes = findViewById(R.id.etFlashNotes);
        etFlashCount = findViewById(R.id.etFlashCount);
        btnFlashBrowse = findViewById(R.id.btnFlashBrowse);
        btnFlashGenerate = findViewById(R.id.btnFlashGenerate);
        tvFlashResultTitle = findViewById(R.id.tvFlashResultTitle);
        flashcardListContainer = findViewById(R.id.flashcardListContainer);

        api = RetrofitAPI.getAuthApi();

        btnFlashBrowse.setOnClickListener(v ->
                Toast.makeText(this, "File picker for flashcards coming soon", Toast.LENGTH_SHORT).show()
        );

        btnFlashGenerate.setOnClickListener(v -> onGenerateFlashcards());

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        // Reuse the same nav helper for now so design/behavior match NotesSummarizer
        NotesNavUtils.setupNotesBottomNav(bottomNav, this, R.id.nav_notes_generate);
    }

    private void onGenerateFlashcards() {
        String notes = etFlashNotes.getText().toString().trim();
        String countStr = etFlashCount.getText().toString().trim();

        if (notes.isEmpty()) {
            Toast.makeText(this, "Please enter or upload notes", Toast.LENGTH_SHORT).show();
            return;
        }

        int numCards = 10; // sensible default if user leaves it blank
        if (!countStr.isEmpty()) {
            try {
                numCards = Integer.parseInt(countStr);
                if (numCards <= 0) numCards = 10;
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid number of flashcards", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        btnFlashGenerate.setEnabled(false);
        btnFlashGenerate.setText("Generating...");
        flashcardListContainer.setVisibility(View.GONE);
        tvFlashResultTitle.setVisibility(View.GONE);

        int userId = getSharedPreferences("notly_prefs", MODE_PRIVATE)
                .getInt("user_id", -1);
        if (userId == -1) {
            Toast.makeText(this, "Please sign in again", Toast.LENGTH_SHORT).show();
            resetGenerateButton();
            return;
        }

        FlashcardGenerationRequest request =
                new FlashcardGenerationRequest(notes, numCards, "AI Flashcards");

        api.generateFlashcards(userId, request).enqueue(new Callback<FlashcardGenerationResponse>() {
            @Override
            public void onResponse(Call<FlashcardGenerationResponse> call, Response<FlashcardGenerationResponse> response) {
                resetGenerateButton();

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(FlashcardGeneratorActivity.this,
                            "Failed to generate flashcards", Toast.LENGTH_SHORT).show();
                    return;
                }

                displayFlashcards(response.body());
            }

            @Override
            public void onFailure(Call<FlashcardGenerationResponse> call, Throwable t) {
                resetGenerateButton();
                Toast.makeText(FlashcardGeneratorActivity.this,
                        "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayFlashcards(FlashcardGenerationResponse response) {
        List<FlashcardItem> cards = response.getCards();
        if (cards == null || cards.isEmpty()) {
            Toast.makeText(this, "No flashcards were returned", Toast.LENGTH_SHORT).show();
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        flashcardListContainer.removeAllViews();
        for (FlashcardItem card : cards) {
            View view = inflater.inflate(R.layout.item_flashcard, flashcardListContainer, false);
            TextView tvFront = view.findViewById(R.id.tvFlashFront);
            TextView tvBack = view.findViewById(R.id.tvFlashBack);
            tvFront.setText(card.getFront());
            tvBack.setText(card.getBack());
            flashcardListContainer.addView(view);
        }

        String title = response.getTitle();
        tvFlashResultTitle.setText(title == null || title.isEmpty() ? "Generated flashcards" : title);
        tvFlashResultTitle.setVisibility(View.VISIBLE);
        flashcardListContainer.setVisibility(View.VISIBLE);
    }

    private void resetGenerateButton() {
        btnFlashGenerate.setEnabled(true);
        btnFlashGenerate.setText("✨ Generate Flashcards");
    }
}
