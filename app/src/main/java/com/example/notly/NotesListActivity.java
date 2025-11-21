package com.example.notly;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {

    private RecyclerView notesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make sure this matches your xml file name: activity_notes_list.xml or notes_list_activity.xml
        setContentView(R.layout.notes_list_activity);

        // Bottom nav
        BottomNavigationView bottomNav = findViewById(R.id.notesBottomNav);
        NotesNavUtils.setupNotesBottomNav(
                bottomNav,
                this,
                R.id.nav_notes_list
        );

        // ✅ RecyclerView setup
        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> dummyNotes = buildDummyNotes();
        NotesListAdapter adapter = new NotesListAdapter(dummyNotes);
        notesRecyclerView.setAdapter(adapter);
    }

    private List<String> buildDummyNotes() {
        List<String> list = new ArrayList<>();
        list.add("Photosynthesis Lecture Notes");
        list.add("Operating Systems — CPU Scheduling");
        list.add("Genetics: Mendel’s Laws Summary");
        return list;
    }
}
