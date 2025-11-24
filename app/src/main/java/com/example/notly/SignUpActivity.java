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

public class SignUpActivity extends AppCompatActivity {

    private AuthApi authApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        if (SessionManager.shouldRemember(this)
                && SessionManager.getUserId(this) != -1) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }
        TextView forgotPassword = findViewById(R.id.tvForgotPassword);
        if (forgotPassword != null) {
            forgotPassword.setOnClickListener(v ->
                    startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class))
            );
        }


        authApi = RetrofitAPI.getAuthApi();

        EditText firstNameEt   = findViewById(R.id.FirstName);
        EditText lastNameEt    = findViewById(R.id.LastName);
        EditText emailEt       = findViewById(R.id.Email);
        EditText passwordEt    = findViewById(R.id.Password);
        EditText confirmPassEt = findViewById(R.id.ConfirmPassword);
        TextView signUpBtn     = findViewById(R.id.btnSignUp);
        TextView goToLoginBtn  = findViewById(R.id.GoToLogin);

        goToLoginBtn.setOnClickListener(v ->
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));

        signUpBtn.setOnClickListener(v -> {

            String firstName  = firstNameEt.getText().toString().trim();
            String lastName   = lastNameEt.getText().toString().trim();
            String email      = emailEt.getText().toString().trim();
            String password   = passwordEt.getText().toString();
            String verifyPass = confirmPassEt.getText().toString();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
                    || password.isEmpty() || verifyPass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(verifyPass)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            SignUpRequest req = new SignUpRequest(
                    firstName,
                    lastName,
                    email,
                    password,
                    verifyPass
            );

            authApi.signUp(req).enqueue(new Callback<SignUpResponse>() {
                @Override
                public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(SignUpActivity.this,
                                "Account created! Please log in.",
                                Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        String raw = null;
                        try {
                            if (response.errorBody() != null) {
                                raw = response.errorBody().string();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String msg = "Sign up failed (code " + response.code() + ")";
                        if (raw != null && !raw.trim().isEmpty()) {
                            msg = raw;
                        }

                        android.util.Log.e("SIGNUP", "code=" + response.code() + " raw=" + raw);
                        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<SignUpResponse> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this,
                            "Network error: " + t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
