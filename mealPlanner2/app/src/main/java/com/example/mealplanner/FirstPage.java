package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstPage extends AppCompatActivity {

    Button btnOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpen = (Button) findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSecondPage();
            }
        });
    }

    public void openSecondPage(){
        Intent intent = new Intent(this, com.example.mealplanner.SecondPage.class);
        startActivity(intent);
    }
}