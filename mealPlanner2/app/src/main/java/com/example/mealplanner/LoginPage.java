package com.example.mealplanner;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private DatabaseHelper databaseHelper; // Declare the database helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        Button loginButton = findViewById(R.id.login_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Input validation: Check if either field is empty
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginPage.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return; // Exit the method if either field is empty
                }

                // Check if the username and password are valid
                if (databaseHelper.checkUser(username, password)) {
                    // Successful login
                    Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    // Intent to start the HomePage activity
                    Intent intent = new Intent(LoginPage.this, HomePage.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Failed login
                    Toast.makeText(LoginPage.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
