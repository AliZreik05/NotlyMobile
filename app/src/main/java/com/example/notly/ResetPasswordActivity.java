package com.example.notly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    private AuthApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_activity);

        api = RetrofitAPI.getAuthApi();

        EditText emailEt = findViewById(R.id.Email);
        EditText newPassEt = findViewById(R.id.NewPassword);
        EditText confirmPassEt = findViewById(R.id.ConfirmNewPassword);
        TextView resetBtn = findViewById(R.id.btnReset);

        resetBtn.setOnClickListener(v -> {
            String email = emailEt.getText().toString().trim();
            String newPass = newPassEt.getText().toString();
            String confirmPass = confirmPassEt.getText().toString();

            if (email.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            ResetPasswordRequest req = new ResetPasswordRequest(email, newPass, confirmPass);

            api.resetPassword(req).enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ResetPasswordActivity.this,
                                "Password reset successfully",
                                Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(ResetPasswordActivity.this,
                                "Reset failed",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    Toast.makeText(ResetPasswordActivity.this,
                            "Network error: " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
