package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        Button loginButton = findViewById(R.id.login_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start the HomePage activity
                Intent intent = new Intent(LoginPage.this, HomePage.class);
                startActivity(intent);
                // Optional: finish the LoginPage so the user can't navigate back
                finish();
            }
        });
    }
}