package com.example.notly;

import android.app.Activity;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotesNavUtils {

    public static void setupNotesBottomNav(BottomNavigationView bottomNav,
                                           Activity activity,
                                           int selectedItemId) {

        bottomNav.setSelectedItemId(selectedItemId);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == selectedItemId) return true;

            boolean handled = false;

            if (id == R.id.nav_notes_generate) {
                activity.startActivity(new Intent(activity, NotesSummarizerActivity.class));
                handled = true;

            } else if (id == R.id.nav_notes_list) {
                activity.startActivity(new Intent(activity, NotesListActivity.class));
                handled = true;

            } else if (id == R.id.nav_notes_profile) {
                activity.startActivity(new Intent(activity, HomeActivity.class));
                handled = true;
            }

            if (handled) {
                activity.overridePendingTransition(0, 0);
                activity.finish();
            }

            return handled;
        });
    }
}
