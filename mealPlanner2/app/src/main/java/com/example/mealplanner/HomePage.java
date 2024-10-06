package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    Button btnSet;
    ImageButton btnBreakfast, btnLunch, btnDinner, btnSnack, btnMidSnack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        btnSet = findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirstPage();
            }
        });

        btnBreakfast = findViewById(R.id.btnBreakfast);
        btnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBookBreakfast();
            }
        });

        btnLunch = findViewById(R.id.btnLunch);
        btnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLunchPage();
            }
        });

        btnDinner = findViewById(R.id.btnDinner);
        btnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDinnerPage();
            }
        });

        btnSnack = findViewById(R.id.btnSnack);
        btnSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMeriendaPage();
            }
        });

        btnMidSnack = findViewById(R.id.btnMidSnack);
        btnMidSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMidnightSnackPage();
            }
        });
    }

    public void openFirstPage() {
        Intent intent = new Intent(this, FirstPage.class);
        startActivity(intent);
    }

    public void openBookBreakfast() {
        Intent intent = new Intent(this, BookBreakfast.class);
        startActivity(intent);
    }

    // Placeholder methods for additional button actions
    public void openLunchPage() {
        Intent intent = new Intent(this, LunchPage.class);
        startActivity(intent);
    }

    public void openDinnerPage() {
        Intent intent = new Intent(this, DinnerPage.class);
        startActivity(intent);
    }

    public void openMeriendaPage() {
        Intent intent = new Intent(this, MeriendaPage.class);
        startActivity(intent);
    }

    public void openMidnightSnackPage() {
        Intent intent = new Intent(this, MidnightSnackPage.class);
        startActivity(intent);
    }
}
