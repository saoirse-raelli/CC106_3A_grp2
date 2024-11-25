package com.example.prepmate.calendar.midnightsnackscalendar;

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

public class MidnightSnacksCalendarActivity extends AppCompatActivity {
    private RecyclerView midnightsnacksRecyclerView;
    private MidnightSnacksCalendarAdapter midnightsnacksAdapter;
    private List<RecipeCalendar> midnightsnacksRecipes; // List of snacks recipes
    private String selectedDate; // Holds the selected date
    private int userId; // Holds the user ID

    private static final String TAG = "MidnightSnacksCalendarActivity"; // Logging tag
    private static final String PREFS_NAME = "DatePrefs"; // SharedPreferences name
    private static final String LOGIN_PREFS_NAME = "LoginPrefs"; // Login SharedPreferences
    private static final String SELECTED_DATE_KEY = "selectedDate"; // Key for the selected date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midnight_snacks_calendar);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Initialize RecyclerView
        midnightsnacksRecyclerView = findViewById(R.id.midnightSnacksRecyclerView);
        midnightsnacksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        midnightsnacksRecipes = new ArrayList<>(); // Initialize the recipe list

        // Retrieve user ID
        userId = getLoggedInUserId();
        if (userId == -1) {
            Log.e(TAG, "User not logged in. User ID not found.");
            finish(); // Exit the activity if no user is logged in
            return;
        }

        // Retrieve the selected date from SharedPreferences or Intent
        selectedDate = getSelectedDate();
        if (selectedDate == null) {
            Intent intent = getIntent();
            selectedDate = intent.getStringExtra("SELECTED_DATE");
            if (selectedDate == null) {
                selectedDate = "default_date"; // Set a default date if none is passed
            }
            saveSelectedDate(selectedDate);
        }

        // Load snacks recipes for the user
        loadMidnightSnacksRecipes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle navigation when back button is pressed
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMidnightSnacksRecipes() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getAllMidnightSnacksRecipesForCalendar();

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        // Retrieve recipe details from the cursor
                        int retrievedUserId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID));
                        if (retrievedUserId == userId) { // Filter recipes by user ID
                            int midnightsnacksId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MIDNIGHT_SNACKS_ID));
                            String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MIDNIGHT_SNACKS_TITLE));
                            int hours = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MIDNIGHT_SNACKS_HOURS));
                            int minutes = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MIDNIGHT_SNACKS_MINUTES));

                            // Add a RecipeCalendar object to the list
                            midnightsnacksRecipes.add(new RecipeCalendar(midnightsnacksId, title, hours, minutes, "Midnight Snacks", retrievedUserId));
                        }
                    } while (cursor.moveToNext());
                }
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Error accessing cursor data", e);
            } finally {
                cursor.close(); // Ensure the cursor is closed
            }
        } else {
            Log.d(TAG, "No midnight snacks recipes found for user ID: " + userId);
        }

        // Set the adapter with the loaded recipes
        midnightsnacksAdapter = new MidnightSnacksCalendarAdapter(midnightsnacksRecipes, selectedDate, this, userId);
        midnightsnacksRecyclerView.setAdapter(midnightsnacksAdapter);
    }

    // Retrieve the logged-in user's ID
    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_PREFS_NAME, MODE_PRIVATE);
        int loggedInUserId = sharedPreferences.getInt("user_id", -1); // Default to -1 if not found
        Log.d(TAG, "Retrieved user ID: " + loggedInUserId);
        return loggedInUserId;
    }

    // Save the selected date in SharedPreferences
    private void saveSelectedDate(String date) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SELECTED_DATE_KEY, date);
        editor.apply();
    }

    // Retrieve the selected date from SharedPreferences
    private String getSelectedDate() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(SELECTED_DATE_KEY, null); // Default to null if not found
    }
}
