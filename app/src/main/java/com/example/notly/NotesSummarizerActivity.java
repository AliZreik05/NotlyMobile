package com.example.notly;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class NotesSummarizerActivity extends AppCompatActivity {

    EditText etNotes;
    Button btnBrowse, btnGenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_summarizer);

        etNotes = findViewById(R.id.etNotes);
        btnBrowse = findViewById(R.id.btnBrowse);
        btnGenerate = findViewById(R.id.btnGenerate);

        btnBrowse.setOnClickListener(v ->
                Toast.makeText(this, "File picker coming soon", Toast.LENGTH_SHORT).show()
        );

        btnGenerate.setOnClickListener(v -> {
            if (etNotes.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter or upload notes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Generating summary...", Toast.LENGTH_SHORT).show();
            }
        });
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        NotesNavUtils.setupNotesBottomNav(bottomNav, this, R.id.nav_notes_generate);

    }
}
