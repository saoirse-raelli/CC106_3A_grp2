package com.example.prepmate.home.midnightsnacksrecipes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.R;
import java.util.ArrayList;

public class MidnightRecipesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    ArrayList<String> newMidnightSnackId, midnightsnackTitle, hours, minutes, ingredients, procedures;
    MidnightAdapter midnightAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midnight_recipes);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.midnight_recipes_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back icon


        // Initialize DatabaseHelper and lists
        databaseHelper = new DatabaseHelper(this);
        newMidnightSnackId = new ArrayList<>();
        midnightsnackTitle = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        ingredients = new ArrayList<>();  // Initialize ingredients list
        procedures = new ArrayList<>();

        storeDataInArrays();

        // Set up the RecyclerView and adapter
        midnightAdapter = new MidnightAdapter(MidnightRecipesActivity.this, this, newMidnightSnackId, midnightsnackTitle, hours, minutes, ingredients, procedures);
        recyclerView.setAdapter(midnightAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MidnightRecipesActivity.this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu with the 'Add Recipe' item
        getMenuInflater().inflate(R.menu.toolbar_add_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_recipe) {
            // Handle the 'Add Recipe' action
            Intent intent = new Intent(MidnightRecipesActivity.this, AddMidnightActivity.class);
            startActivityForResult(intent, 1);  // Pass the request code 1
            return true;
        }

        // Handle the back navigation
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Or use finish() to close the activity
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Method to fetch data from the database
    void storeDataInArrays() {
        // Clear the existing data in the lists
        newMidnightSnackId.clear();
        midnightsnackTitle.clear();
        hours.clear();
        minutes.clear();
        ingredients.clear();
        procedures.clear();

        // Get the user_id of the logged-in user
        int userId = getLoggedInUserId();
        if (userId == -1) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            return; // Exit the method if no user is logged in
        }


        Cursor cursor = databaseHelper.readAllMidnightSnacksRecipes(userId);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                newMidnightSnackId.add(cursor.getString(0));  // ID
                midnightsnackTitle.add(cursor.getString(1));  // Title
                hours.add(cursor.getString(2));     // Hours
                minutes.add(cursor.getString(3));   // Minutes
                ingredients.add(cursor.getString(4)); // Ingredients
                procedures.add(cursor.getString(5));  // Procedures
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            storeDataInArrays();
            midnightAdapter.notifyDataSetChanged();
        }
    }
    // Method to get the logged-in user's ID (You should implement this based on your login system)
    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1); // Return the user_id stored during login, or -1 if not logged in
    }
}