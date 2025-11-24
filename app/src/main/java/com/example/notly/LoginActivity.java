package com.example.notly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private AuthApi authApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1) Auto-login if remembered
        if (SessionManager.shouldRemember(this)
                && SessionManager.getUserId(this) != -1) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.login_activity);

        authApi = RetrofitAPI.getAuthApi();

        EditText emailEt    = findViewById(R.id.Email);
        EditText passwordEt = findViewById(R.id.Password);
        TextView loginBtn   = findViewById(R.id.btnLogin);
        TextView goToSignUp = findViewById(R.id.GoToSignUp);
        CheckBox rememberCb = findViewById(R.id.rememberMeCheckBox);

        // Go to Sign Up
        if (goToSignUp != null) {
            goToSignUp.setOnClickListener(v ->
                    startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
        }
        TextView forgotPassword = findViewById(R.id.tvForgotPassword);
        if (forgotPassword != null) {
            forgotPassword.setOnClickListener(v ->
                    startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class))
            );
        }


        // Login click
        loginBtn.setOnClickListener(v -> {
            String email    = emailEt.getText().toString().trim();
            String password = passwordEt.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            LoginRequest req = new LoginRequest(email, password);

            authApi.login(req).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponse token = response.body();

                        // ⚠ Make sure LoginResponse has a userId field + getter
                        int userId = token.getUserId();

                        // Save token (optional, as you had) - still local
                        getSharedPreferences("notly_prefs", MODE_PRIVATE)
                                .edit()
                                .putString("access_token", token.getAccessToken())
                                .putString("token_type", token.getTokenType())
                                .apply();

                        // Save session info for this device
                        SessionManager.saveUserId(LoginActivity.this, userId);
                        SessionManager.setRememberMe(LoginActivity.this, rememberCb.isChecked());

                        Toast.makeText(LoginActivity.this,
                                "Logged in successfully",
                                Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        String error = "Login failed";

                        try {
                            if (response.errorBody() != null) {
                                String json = response.errorBody().string();
                                ApiError apiError = new Gson().fromJson(json, ApiError.class);
                                if (apiError != null && apiError.detail != null) {
                                    error = apiError.detail;
                                }
                            }
                        } catch (Exception ignored) {}

                        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,
                            "Network error: " + t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
