package com.example.prepmate;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prepmate.home.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TodayActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private TodayAdapter todayAdapter;
    private int userId;
    private static final String LOGIN_PREFS_NAME = "LoginPrefs";  // SharedPreferences file name
    private ArrayList<RecipeCalendar> recipeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Retrieve userId from SharedPreferences
        userId = getLoggedInUserId();

        // Initialize RecyclerView and TodayAdapter
        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        todayAdapter = new TodayAdapter(recipeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(todayAdapter);

        // Load recipes for the current date
        String currentDate = getCurrentDate();
        loadRecipesForDate(currentDate, userId);
    }

    /**
     * Fetch and load recipes for a specific date.
     *
     * @param date The date in "YYYY-MM-DD" format.
     * @param userId The logged-in user's ID.
     */
    private void loadRecipesForDate(String date, int userId) {
        recipeList.clear();

        // Fetch calendar entry for the given date
        Cursor cursor = databaseHelper.getCalendarEntryByDate(date, userId);
        if (cursor != null && cursor.moveToFirst()) {
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
            addPlaceholders(); // Add placeholders if no recipes are found
        }

        todayAdapter.notifyDataSetChanged(); // Notify adapter of data change
    }

    /**
     * Adds a recipe to the list based on its ID and category.
     *
     * @param recipeId The ID of the recipe.
     * @param category The category (e.g., "Breakfast").
     * @param userId The logged-in user's ID.
     */
    private void addRecipeToList(int recipeId, String category, int userId) {
        if (recipeId != -1) {
            RecipeCalendar recipe = databaseHelper.getRecipeById(recipeId, category, userId);
            if (recipe != null) {
                recipeList.add(recipe);
            } else {
                recipeList.add(new RecipeCalendar(-1, "No " + category, 0, 0, category, userId));
            }
        } else {
            recipeList.add(new RecipeCalendar(-1, "No " + category, 0, 0, category, userId));
        }
    }

    /**
     * Adds placeholders for all meal categories.
     */
    private void addPlaceholders() {
        recipeList.add(new RecipeCalendar(-1, "No Breakfast", 0, 0, "Breakfast", 0));
        recipeList.add(new RecipeCalendar(-1, "No Lunch", 0, 0, "Lunch", 0));
        recipeList.add(new RecipeCalendar(-1, "No Snacks", 0, 0, "Snacks", 0));
        recipeList.add(new RecipeCalendar(-1, "No Dinner", 0, 0, "Dinner", 0));
        recipeList.add(new RecipeCalendar(-1, "No Midnight Snacks", 0, 0, "Midnight Snacks", 0));
    }

    /**
     * Gets the current date in "YYYY-MM-DD" format.
     *
     * @return The current date as a string.
     */
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    /**
     * Retrieves the logged-in user's ID from SharedPreferences.
     *
     * @return The logged-in user's ID.
     */
    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_PREFS_NAME, MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1); // Default value is -1 if not found
        Log.d("TodayActivity", "Retrieved user_id: " + userId); // Log to verify
        return userId;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
