package com.example.notly;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

public class TopBarUtils {

    public static void setupTopBar(Activity activity) {
        ImageView profileIcon = activity.findViewById(R.id.homeIcon);
        if (profileIcon != null) {
            profileIcon.setImageResource(R.drawable.ic_logout);
            profileIcon.setOnClickListener(v -> {
                SessionManager.clear(activity);

                Intent i = new Intent(activity, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(i);
            });
        }
    }
}
