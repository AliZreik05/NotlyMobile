package com.example.notly;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FlashcardGeneratorActivity extends AppCompatActivity {

    EditText etFlashNotes, etFlashCount;
    Button btnFlashBrowse, btnFlashGenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_generator);

        etFlashNotes = findViewById(R.id.etFlashNotes);
        etFlashCount = findViewById(R.id.etFlashCount);
        btnFlashBrowse = findViewById(R.id.btnFlashBrowse);
        btnFlashGenerate = findViewById(R.id.btnFlashGenerate);

        btnFlashBrowse.setOnClickListener(v ->
                Toast.makeText(this, "File picker for flashcards coming soon", Toast.LENGTH_SHORT).show()
        );

        btnFlashGenerate.setOnClickListener(v -> {
            String notes = etFlashNotes.getText().toString().trim();
            String countStr = etFlashCount.getText().toString().trim();

            if (notes.isEmpty()) {
                Toast.makeText(this, "Please enter or upload notes", Toast.LENGTH_SHORT).show();
            } else {
                // later you'll call the backend here
                Toast.makeText(this, "Generating flashcards" +
                                (countStr.isEmpty() ? "..." : " (" + countStr + " cards)..."),
                        Toast.LENGTH_SHORT).show();
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        // Reuse the same nav helper for now so design/behavior match NotesSummarizer
        NotesNavUtils.setupNotesBottomNav(bottomNav, this, R.id.nav_notes_generate);
        // Later, if you make a separate Flashcards bottom nav, you can change this.
    }
}