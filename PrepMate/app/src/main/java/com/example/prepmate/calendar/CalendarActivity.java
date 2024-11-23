package com.example.prepmate.calendar;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prepmate.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.prepmate.DatabaseHelper;

import com.example.prepmate.RecipeCalendar;

public class CalendarActivity extends AppCompatActivity {

    // UI Elements
    private CalendarView calendarView;
    private TextView selectedDateText;
    private Button addRecipesButton;
    private RecyclerView recipesRecyclerView;

    // Adapter for RecyclerView
    private CalendarAdapter calendarAdapter;

    // List to hold recipes
    private List<RecipeCalendar> recipeList;

    // Database helper instance
    private DatabaseHelper databaseHelper;

    // Variable to hold the selected date
    private String selectedDate;

    // SharedPreferences constants
    private static final String PREFS_NAME = "DatePrefs";
    private static final String SELECTED_DATE_KEY = "selectedDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initialize UI elements
        calendarView = findViewById(R.id.calendarView);
        selectedDateText = findViewById(R.id.selectedDateText);
        addRecipesButton = findViewById(R.id.addRecipesButton);
        recipesRecyclerView = findViewById(R.id.recipesRecyclerView);

        // Initialize DatabaseHelper and recipe list
        databaseHelper = new DatabaseHelper(this);
        recipeList = new ArrayList<>();

        // Set background color for RecyclerView
        recipesRecyclerView.setBackgroundColor(0xFFFFC0CB);

        // Set up RecyclerView
        calendarAdapter = new CalendarAdapter(recipeList);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipesRecyclerView.setAdapter(calendarAdapter);

        // Check if we need to reset the activity
        boolean reset = getIntent().getBooleanExtra("reset", false);
        if (reset) {
            clearSelectedDate();
            Log.d("ReceiveIntent", "Received reset = " + reset);
        }

        // Set a listener for the CalendarView to update the button text when a date is selected
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Format the selected date as needed
                selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth; // Example format
                selectedDateText.setText("Selected Date: " + selectedDate);
                addRecipesButton.setText("Select a Meal"); // Update button text
                loadRecipesForDate(selectedDate); // Load recipes for the newly selected date
            }
        });

        // Load the previously selected date from SharedPreferences
        String storedDate = getSelectedDate();
        if (storedDate != null) {
            // If a date is stored, set the CalendarView to that date
            selectedDate = storedDate;
            selectedDateText.setText("Selected Date: " + selectedDate);
            calendarView.setDate(convertDateStringToMillis(selectedDate), true, true);
            loadRecipesForDate(selectedDate); // Load recipes for the selected date
        } else {
            // Initialize with today's date if no date is stored
            selectedDate = String.valueOf(System.currentTimeMillis());
            calendarView.setDate(System.currentTimeMillis(), true, true);
            addRecipesButton.setText("Select a Date");
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the back navigation
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Method to save the selected date
    private void saveSelectedDate(String date) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SELECTED_DATE_KEY, date);
        editor.apply();
    }

    // Method to retrieve the selected date
    private String getSelectedDate() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(SELECTED_DATE_KEY, null); // Return null if no date is stored
    }

    // Method to convert a date string to milliseconds
    private long convertDateStringToMillis(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = sdf.parse(dateString);
            return date != null ? date.getTime() : 0; // Return milliseconds
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis(); // Return current time if parsing fails
        }
    }

    private void loadRecipesForDate(String date) {
        recipeList.clear();

        // Fetch calendar entry for the selected date
        Cursor cursor = databaseHelper.getCalendarEntryByDate(date);
        if (cursor != null && cursor.moveToFirst()) {
            int breakfastId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_BREAKFAST_ID));
            int lunchId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_LUNCH_ID));
            int snacksId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SNACKS_ID));
            int dinnerId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_DINNER_ID));
            int midnightSnacksId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_MIDNIGHT_SNACKS_ID));

            addRecipeToList(breakfastId, "Breakfast");
            addRecipeToList(lunchId, "Lunch");
            addRecipeToList(snacksId, "Snacks");
            addRecipeToList(dinnerId, "Dinner");
            addRecipeToList(midnightSnacksId, "Midnight Snacks");

            cursor.close();
        } else {
            addPlaceholders(); // Add placeholders if no recipes are found
        }

        calendarAdapter.notifyDataSetChanged(); // Notify adapter of data change
    }

    private void addRecipeToList(int recipeId, String category) {
        if (recipeId != -1) {
            RecipeCalendar recipe = databaseHelper.getRecipeById(recipeId, category);
            if (recipe != null) {
                recipeList.add(recipe);
            } else {
                recipeList.add(new RecipeCalendar(-1, "No " + category, 0, 0, category));
            }
        } else {
            recipeList.add(new RecipeCalendar(-1, "No " + category, 0, 0, category));
        }
    }

    private void addPlaceholders() {
        recipeList.add(new RecipeCalendar(-1, "No Breakfast", 0, 0, "Breakfast"));
        recipeList.add(new RecipeCalendar(-1, "No Lunch", 0, 0, "Lunch"));
        recipeList.add(new RecipeCalendar(-1, "No Snacks", 0, 0, "Snacks"));
        recipeList.add(new RecipeCalendar(-1, "No Dinner", 0, 0, "Dinner"));
        recipeList.add(new RecipeCalendar(-1, "No Midnight Snacks", 0, 0, "Midnight Snacks"));
    }


    // Method to clear the selected date from SharedPreferences
    private void clearSelectedDate() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SELECTED_DATE_KEY); // Remove the stored selected date
        editor.apply();}
}