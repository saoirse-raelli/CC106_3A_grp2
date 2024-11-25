package com.example.prepmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        Button loginButton = findViewById(R.id.login_btn);
        TextView signUpText = findViewById(R.id.sign_up_text);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            int userId = sharedPreferences.getInt("user_id", -1);
            Log.d("LoginActivity", "User already logged in with ID: " + userId);
            navigateToMainActivity();
        }

        // LOGIN BUTTON
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (databaseHelper.checkUser(username, password)) {
                    int userId = databaseHelper.getUserId(username, password);

                    if (userId != -1) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putInt("user_id", userId);
                        editor.apply();

                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        navigateToMainActivity();
                    } else {
                        Log.e("LoginActivity", "Error retrieving user ID for username: " + username);
                        Toast.makeText(LoginActivity.this, "Error retrieving user ID.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("LoginActivity", "Invalid credentials: " + username);
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    usernameInput.setText("");
                    passwordInput.setText("");
                    usernameInput.requestFocus();
                }
            }
        });

        // SIGN UP TEXT REDIRECT TO SIGN UP ACTIVITY
        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

