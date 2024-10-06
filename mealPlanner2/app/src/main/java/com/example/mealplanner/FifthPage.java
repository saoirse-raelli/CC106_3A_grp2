package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class FifthPage extends AppCompatActivity {
    Button btnBack, btnBreakfast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth_page);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSecondPage();
            }
        });


        btnBreakfast = (Button) findViewById(R.id.btnBreakfast);
        btnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBreakfastPage();
            }
        });
    }
    public void openSecondPage(){
        Intent intent = new Intent(this, SecondPage.class);
        startActivity(intent);
    }

    public void openBreakfastPage(){
        Intent intent = new Intent(this, BreakfastPage.class);
        startActivity(intent);
    }
}
