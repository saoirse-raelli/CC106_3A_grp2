package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FourthPage extends AppCompatActivity {
    Button btnBack;
    TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_page);

        headerText = findViewById(R.id.headerText);

        String selectedDate = getIntent().getStringExtra("selectedDate");


        if (selectedDate != null) {
            headerText.setText("Selected Date: " + selectedDate);
        } else {
            headerText.setText("Selected Date: Not Specified");
        }

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openThirdPage();
            }
        });
    }

    public void openThirdPage() {
        Intent intent = new Intent(this, ThirdPage.class);
        startActivity(intent);
    }
}
