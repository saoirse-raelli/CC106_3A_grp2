/*
 *HINDI MUNA TO GAGAMITIN
 *
 * NAALIS MUNA TO DAHIL NAG BAWAS NG PAGE DUE TO REVISION*/



package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SecondPage extends AppCompatActivity {

    Button btnBack, btnCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page);

        // Initialize btnBack to navigate to FirstPage
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirstPage();
            }
        });

        // Initialize btnCalendar to navigate to ThirdPage
        btnCalendar = (Button) findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openThirdPage();
            }
        });

    }

    // Method to navigate to FirstPage
    public void openFirstPage() {
        Intent intent = new Intent(this, FirstPage.class);
        startActivity(intent);
    }

    // Method to navigate to ThirdPage
    public void openThirdPage() {
        Intent intent = new Intent(this, ThirdPage.class);
        startActivity(intent);
    }
}
