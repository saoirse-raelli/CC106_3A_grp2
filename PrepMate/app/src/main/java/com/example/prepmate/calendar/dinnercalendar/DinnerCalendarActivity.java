package com.example.prepmate.calendar.dinnercalendar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prepmate.R;
import com.example.prepmate.RecipeCalendar;
import com.example.prepmate.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DinnerCalendarActivity extends AppCompatActivity {
    private RecyclerView dinnerRecyclerView;
    private DinnerCalendarAdapter dinnerAdapter;
    private List<RecipeCalendar> dinnerRecipes; // List of breakfast recipes
    private String selectedDate; // Variable to hold the selected date

    private static final String TAG = "Dinner CalendarActivity"; // Tag for logging
    private static final String PREFS_NAME = "DatePrefs"; // SharedPreferences name
    private static final String SELECTED_DATE_KEY = "selectedDate"; // Key for the selected date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_calendar); // Ensure this layout exists

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        dinnerRecyclerView = findViewById(R.id.dinnerRecyclerView);
        dinnerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dinnerRecipes = new ArrayList<>(); // Initialize the list

        // Retrieve the selected date from SharedPreferences
        selectedDate = getSelectedDate();

        // If no date is found in SharedPreferences, check the Intent
        if (selectedDate == null) {
            Intent intent = getIntent();
            selectedDate = intent.getStringExtra("SELECTED_DATE");
            if (selectedDate == null) {
                selectedDate = "default_date"; // Set a default date if none is passed
            }
            // Save the newly retrieved date in SharedPreferences
            saveSelectedDate(selectedDate);
        }

        // Load breakfast recipes
        loadDinnerRecipes();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the back navigation
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadDinnerRecipes() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getAllDinnerRecipesForCalendar();

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        // Retrieve the id, title, hours, and minutes from the cursor
                        int dinner_id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DINNER_ID));
                        String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DINNER_TITLE));
                        int hours = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DINNER_HOURS));
                        int minutes = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DINNER_MINUTES));

                        // Add RecipeCalendar object with category set to "Breakfast"
                        dinnerRecipes.add(new RecipeCalendar(dinner_id, title, hours, minutes, "Dinner")); // Add category

                    } while (cursor.moveToNext());
                }
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Error accessing cursor data", e);
            } finally {
                cursor.close(); // Ensure the cursor is closed
            }
        }

        // Set the adapter to display the recipes with the selected date
        dinnerAdapter = new DinnerCalendarAdapter(dinnerRecipes, selectedDate, this);
        dinnerRecyclerView.setAdapter(dinnerAdapter); // Ensure the adapter is used
    }

    // Method to save the selected date in SharedPreferences
    private void saveSelectedDate(String date) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SELECTED_DATE_KEY, date);
        editor.apply();
    }

    // Method to retrieve the selected date from SharedPreferences
    private String getSelectedDate() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(SELECTED_DATE_KEY, null); // Return null if no date is stored
    }
}