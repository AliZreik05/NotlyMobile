package com.example.notly;

import android.content.Intent;
import android.os.Bundle;
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
        setContentView(R.layout.login_activity);  // make sure this matches your XML file name

        authApi = RetrofitAPI.getClient().create(AuthApi.class);

        EditText emailEt    = findViewById(R.id.Email);
        EditText passwordEt = findViewById(R.id.Password);
        TextView loginBtn   = findViewById(R.id.btnLogin);
        //TextView goToSignUp = findViewById(R.id.GoToSignUp);  if you have it

        // Go to Sign Up
        //if (goToSignUp != null) {
            //goToSignUp.setOnClickListener(v ->
                   // startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
        //}

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

                        // Save token
                        getSharedPreferences("notly_prefs", MODE_PRIVATE)
                                .edit()
                                .putString("access_token", token.getAccessToken())
                                .putString("token_type", token.getTokenType())
                                .apply();

                        Toast.makeText(LoginActivity.this,
                                "Logged in successfully",
                                Toast.LENGTH_SHORT).show();

                        // Go to home (or Notes/Quiz etc.)
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
