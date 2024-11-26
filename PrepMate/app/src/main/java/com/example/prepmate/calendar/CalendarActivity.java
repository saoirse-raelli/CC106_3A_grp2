package com.example.prepmate.calendar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prepmate.MainActivity;
import com.example.prepmate.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.RecipeCalendar;
import com.example.prepmate.home.HomeFragment;

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
    private static final String DATE_PREFS_NAME = "DatePrefs";
    private static final String LOGIN_PREFS_NAME = "LoginPrefs";
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
        recipesRecyclerView.setBackgroundColor(0xFFF6E3D5); // A soft nude color
        calendarView.setBackgroundColor(0xFFFEF9F7); // A very soft nude color

        // Set up RecyclerView
        calendarAdapter = new CalendarAdapter(recipeList);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipesRecyclerView.setAdapter(calendarAdapter);

        int userId = getLoggedInUserId();
        if (userId == -1) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            Log.e("CalendarActivity", "No logged-in user found.");
            return; // Exit if no user is logged in
        }

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
                selectedDateText.setText("                    " + selectedDate);
                addRecipesButton.setText("Select a Meal"); // Update button text
                loadRecipesForDate(selectedDate, userId); // Load recipes for the newly selected date
                saveSelectedDate(selectedDate);
            }
        });

        // Load the previously selected date from SharedPreferences
        String storedDate = getSelectedDate();
        if (storedDate != null) {
            // If a date is stored, set the CalendarView to that date
            selectedDate = storedDate;
            selectedDateText.setText("                    " + selectedDate);
            calendarView.setDate(convertDateStringToMillis(selectedDate), true, true);
            loadRecipesForDate(selectedDate, userId); // Load recipes for the selected date
            saveSelectedDate(selectedDate);
            addRecipesButton.setText("Select a Meal");
        } else {
            // Initialize with today's date if no date is stored
            selectedDate = String.valueOf(System.currentTimeMillis());
            calendarView.setDate(System.currentTimeMillis(), true, true);
            addRecipesButton.setText("                         SELECT A DATE");
        }
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Navigate back to MainActivity
            Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish(); // Finish the current activity to avoid stacking
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveSelectedDate(String date) {
        SharedPreferences sharedPreferences = getSharedPreferences(DATE_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SELECTED_DATE_KEY, date);
        editor.apply();
    }

    private String getSelectedDate() {
        SharedPreferences sharedPreferences = getSharedPreferences(DATE_PREFS_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(SELECTED_DATE_KEY, null);
    }

    private long convertDateStringToMillis(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = sdf.parse(dateString);
            return date != null ? date.getTime() : 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }
    }

    private void loadRecipesForDate(String date, int userId) {
        recipeList.clear();

        Cursor cursor = databaseHelper.getCalendarEntryByDate(date, userId);
        if (cursor != null && cursor.moveToFirst()) {
            Log.d("CalendarActivity", "Calendar entry found for date: " + date);
            int breakfastId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_BREAKFAST_ID));
            int lunchId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_LUNCH_ID));
            int snacksId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SNACKS_ID));
            int dinnerId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_DINNER_ID));
            int midnightSnacksId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_MIDNIGHT_SNACKS_ID));

            addRecipeToList(breakfastId, "Breakfast", userId);
            addRecipeToList(lunchId, "Lunch", userId);
            addRecipeToList(snacksId, "Snacks", userId);
            addRecipeToList(dinnerId, "Dinner", userId);
            addRecipeToList(midnightSnacksId, "Midnight Snacks", userId);

            cursor.close();
        } else {
            Log.d("CalendarActivity", "No calendar entry found for date: " + date);
            addPlaceholders();
        }

        calendarAdapter.notifyDataSetChanged();
    }

    private void addRecipeToList(int recipeId, String category, int userId) {
        if (recipeId != -1) {
            RecipeCalendar recipe = databaseHelper.getRecipeById(recipeId, category, userId);
            if (recipe != null) {
                recipeList.add(recipe);
            } else {
                Log.d("CalendarActivity", "No recipe found for ID: " + recipeId + " in category: " + category);
                recipeList.add(new RecipeCalendar(-1, "No " + category, 0, 0, category, userId));
            }
        } else {
            recipeList.add(new RecipeCalendar(-1, "No " + category, 0, 0, category, userId));
        }
    }

    private void addPlaceholders() {
        recipeList.add(new RecipeCalendar(-1, "No Breakfast", 0, 0, "Breakfast", 0));
        recipeList.add(new RecipeCalendar(-1, "No Lunch", 0, 0, "Lunch", 0));
        recipeList.add(new RecipeCalendar(-1, "No Snacks", 0, 0, "Snacks", 0));
        recipeList.add(new RecipeCalendar(-1, "No Dinner", 0, 0, "Dinner", 0));
        recipeList.add(new RecipeCalendar(-1, "No Midnight Snacks", 0, 0, "Midnight Snacks", 0));
    }

    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_PREFS_NAME, MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);
        Log.d("CalendarActivity", "Retrieved user_id: " + userId);
        return userId;
    }

    // Method to clear the selected date from SharedPreferences
    private void clearSelectedDate() {
        SharedPreferences sharedPreferences = getSharedPreferences(DATE_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SELECTED_DATE_KEY); // Remove the stored selected date
        selectedDateText.setText("");
        editor.apply();
    }
}