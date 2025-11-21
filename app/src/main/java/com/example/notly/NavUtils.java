package com.example.notly;

import android.app.Activity;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavUtils {

    public static void setupBottomNav(BottomNavigationView bottomNav,
                                      Activity activity,
                                      int selectedItemId) {

        bottomNav.setSelectedItemId(selectedItemId);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == selectedItemId) return true;

            boolean handled = false;

            if (id == R.id.nav_home) {
                activity.startActivity(new Intent(activity, HomeActivity.class));
                handled = true;

            } else if (id == R.id.nav_generate) {
                activity.startActivity(new Intent(activity, GenerateQuizActivity.class));
                handled = true;

            } else if (id == R.id.nav_history) {
                activity.startActivity(new Intent(activity, HistoryActivity.class));
                handled = true;

            } else if (id == R.id.nav_profile) {
                // later when you add ProfileActivity:
                // activity.startActivity(new Intent(activity, ProfileActivity.class));
                // handled = true;
            }

            if (handled) {
                activity.overridePendingTransition(0, 0);
                activity.finish();
            }

            return handled;
        });
    }
}
