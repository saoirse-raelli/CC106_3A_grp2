package com.example.prepmate.home.snacksrecipes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.R;
import com.example.prepmate.home.dinnerrecipes.DinnerAdapter;
import com.example.prepmate.home.dinnerrecipes.DinnerRecipesActivity;
import com.example.prepmate.home.lunchrecipes.AddLunchActivity;
import com.example.prepmate.home.lunchrecipes.LunchRecipesActivity;

import java.util.ArrayList;

public class SnacksRecipesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    ArrayList<String> newSnacksId, snacksTitle, hours, minutes, ingredients, procedures;
    SnacksAdapter snacksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks_recipes);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.snacks_recipes_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back icon

        // Initialize DatabaseHelper and lists
        databaseHelper = new DatabaseHelper(this);
        newSnacksId = new ArrayList<>();
        snacksTitle = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        ingredients = new ArrayList<>();  // Initialize ingredients list
        procedures = new ArrayList<>();

        storeDataInArrays();

        // Set up the RecyclerView and adapter
        snacksAdapter = new SnacksAdapter(SnacksRecipesActivity.this, this, newSnacksId, snacksTitle, hours, minutes, ingredients, procedures);
        recyclerView.setAdapter(snacksAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SnacksRecipesActivity.this));
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
            Intent intent = new Intent(SnacksRecipesActivity.this, AddSnacksActivity.class);
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

    void storeDataInArrays() {
        // Clear the existing data in the lists
        newSnacksId.clear();
        snacksTitle.clear();
        hours.clear();
        minutes.clear();
        ingredients.clear();
        procedures.clear();

        Cursor cursor = databaseHelper.readAllSnacksRecipes();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                newSnacksId.add(cursor.getString(0));  // ID
                snacksTitle.add(cursor.getString(1));  // Title
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
            snacksAdapter.notifyDataSetChanged();
        }
    }

}