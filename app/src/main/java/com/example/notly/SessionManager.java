package com.example.notly;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "notly_prefs";
    private static final String KEY_USER_ID = "user_id";

    public static void saveUserId(Context context, int userId) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_USER_ID, userId).apply();
    }

    public static int getUserId(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_USER_ID, -1);  // -1 = not logged in
    }

    public static void clear(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}
