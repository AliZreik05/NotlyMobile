package com.example.notly;

import android.app.Activity;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuizNavUtils {

    public static void setupQuizBottomNav(BottomNavigationView bottomNav,
                                          Activity activity,
                                          int selectedItemId) {

        bottomNav.setSelectedItemId(selectedItemId);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            // already on this tab
            if (id == selectedItemId) return true;

            boolean handled = false;

            if (id == R.id.nav_generate) {
                activity.startActivity(new Intent(activity, GenerateQuizActivity.class));
                handled = true;

            } else if (id == R.id.nav_quiz_history) {
                activity.startActivity(new Intent(activity, HistoryActivity.class));
                handled = true;

            } else if (id == R.id.nav_quiz_profile) {
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
