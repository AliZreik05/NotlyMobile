package com.example.notly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        TopBarUtils.setupTopBar(this);
        View cardSummarizer = findViewById(R.id.cardSummarizer);
        View cardQuiz = findViewById(R.id.cardQuiz);
        View cardFlashcards = findViewById(R.id.cardFlashcards);
        View cardTranscriber = findViewById(R.id.cardTranscriber);

        cardSummarizer.setOnClickListener(v ->
                startActivity(new Intent(this, NotesSummarizerActivity.class)));

        cardQuiz.setOnClickListener(v ->
                startActivity(new Intent(this, GenerateQuizActivity.class)));

        cardFlashcards.setOnClickListener(v ->
                startActivity(new Intent(this, FlashcardGeneratorActivity.class))); // stub for now

        //cardTranscriber.setOnClickListener(v ->
         //       startActivity(new Intent(this, TranscriberActivity.class))); // stub for now
    }
}
