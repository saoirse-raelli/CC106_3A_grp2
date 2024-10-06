package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdPage extends AppCompatActivity {

    Button btnBack;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_page);

        calendarView = findViewById(R.id.calendarView);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirstPage();
            }
        });

                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        Toast.makeText(ThirdPage.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(ThirdPage.this, FourthPage.class);
                        intent.putExtra("selectedDate", selectedDate);
                        startActivity(intent);
                    }
                }

        );
    }
    public void openFirstPage(){
        Intent intent = new Intent(this, FirstPage.class);
        startActivity(intent);
    }
}