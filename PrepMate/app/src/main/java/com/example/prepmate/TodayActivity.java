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

    private ArrayList<Today> recipeList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseHelper = new DatabaseHelper(this);

        userId = getLoggedInUserId();

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        todayAdapter = new TodayAdapter(recipeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(todayAdapter);

        String currentDate = getCurrentDate();
        loadRecipesForDate(currentDate, userId);
    }


    private void loadRecipesForDate(String date, int userId) {
        recipeList.clear();

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
            addPlaceholders();
        }

        todayAdapter.notifyDataSetChanged();
    }

    private void addRecipeToList(int recipeId, String category, int userId) {
        if (recipeId != -1) {

            Today recipe = databaseHelper.getRecipeTodayById(recipeId, category, userId);
            if (recipe != null) {
                recipeList.add(recipe);
            } else {
                recipeList.add(new Today(-1, "No " + category, 0, 0, category, userId, "None","None"));
            }
        }
    }


    private void addPlaceholders() {

        recipeList.add(new Today(-1, "No Breakfast", 0, 0, "Breakfast", 0, "None", "None"));
        recipeList.add(new Today(-1, "No Lunch", 0, 0, "Lunch", 0 ,"None", "None"));
        recipeList.add(new Today(-1, "No Snacks", 0, 0, "Snacks", 0, "None", "None"));
        recipeList.add(new Today(-1, "No Dinner", 0, 0, "Dinner", 0, "None", "None"));
        recipeList.add(new Today(-1, "No Midnight Snacks", 0, 0, "Midnight Snacks", 0, "None", "None"));


    }


    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }


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
