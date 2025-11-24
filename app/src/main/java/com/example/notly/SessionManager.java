package com.example.notly;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "notly_prefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_REMEMBER_ME = "remember_me";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // ---- user id ----
    public static void saveUserId(Context context, int userId) {
        getPrefs(context).edit()
                .putInt(KEY_USER_ID, userId)
                .apply();
    }

    public static int getUserId(Context context) {
        return getPrefs(context).getInt(KEY_USER_ID, -1);  // -1 = not logged in
    }

    // ---- remember me ----
    public static void setRememberMe(Context context, boolean remember) {
        getPrefs(context).edit()
                .putBoolean(KEY_REMEMBER_ME, remember)
                .apply();
    }

    public static boolean shouldRemember(Context context) {
        return getPrefs(context).getBoolean(KEY_REMEMBER_ME, false);
    }

    // ---- clear everything on logout ----
    public static void clear(Context context) {
        getPrefs(context).edit().clear().apply();
    }
}
