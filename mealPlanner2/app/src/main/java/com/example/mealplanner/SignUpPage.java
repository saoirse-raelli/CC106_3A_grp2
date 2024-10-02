package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpPage extends AppCompatActivity {
    private EditText lastnameInput;
    private EditText firstnameInput;
    private EditText emailInput;
    private EditText usernameInput;
    private EditText passwordInput;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        lastnameInput = findViewById(R.id.lastname_input);
        firstnameInput = findViewById(R.id.firstname_input);
        emailInput = findViewById(R.id.email_input);
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);

        Button signUpButton = findViewById(R.id.login_btn);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastname = lastnameInput.getText().toString();
                String firstname = firstnameInput.getText().toString();
                String email = emailInput.getText().toString();
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                // Save the input values to the database
                saveToDatabase(lastname, firstname, email, username, password);
            }
        });

        TextView loginText = findViewById(R.id.login_text);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(intent);
            }
        });
    }

    private void saveToDatabase(String lastname, String firstname, String email, String username, String password) {
        databaseHelper.addUser(lastname, firstname, email, username, password);
        Toast.makeText(SignUpPage.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();

        // Intent to start the LoginPage activity
        Intent intent = new Intent(SignUpPage.this, LoginPage.class);
        startActivity(intent);
        finish(); // Optional: finish the SignUpPage so the user can't navigate back
    }
}
