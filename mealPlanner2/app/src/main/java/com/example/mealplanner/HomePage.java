package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    Button btnCreate, btnSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        btnSet = (Button) findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirstPage();
            }

        });
    }
    public void openFirstPage() {
        Intent intent = new Intent(this, FirstPage.class);
        startActivity(intent);
    }
}