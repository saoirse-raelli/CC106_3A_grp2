package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstPage extends AppCompatActivity {

    Button btnOpen, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpen = (Button) findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openThirdPage();
            }
        });

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomePage();
            }
        });
    }

    public void openThirdPage(){
        Intent intent = new Intent(this, com.example.mealplanner.ThirdPage.class);
        startActivity(intent);
    }

    public void openHomePage(){
        Intent intent = new Intent(this, com.example.mealplanner.HomePage.class);
        startActivity(intent);
    }
}