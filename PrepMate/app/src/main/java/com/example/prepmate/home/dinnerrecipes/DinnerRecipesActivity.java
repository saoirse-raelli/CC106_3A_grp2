package com.example.prepmate.home.dinnerrecipes;

import android.content.Intent;
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


public class DinnerRecipesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    ArrayList<String> newDinnerId, dinnerTitle, hours, minutes, ingredients, procedures;
    DinnerAdapter dinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_recipes);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);


        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.dinner_recipes_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back icon

        // Initialize DatabaseHelper and lists
        databaseHelper = new DatabaseHelper(this);
        newDinnerId = new ArrayList<>();
        dinnerTitle = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        ingredients = new ArrayList<>();  // Initialize ingredients list
        procedures = new ArrayList<>();

        storeDataInArrays();

        // Set up the RecyclerView and adapter
        dinnerAdapter = new DinnerAdapter(DinnerRecipesActivity.this, this, newDinnerId, dinnerTitle, hours, minutes, ingredients, procedures);
        recyclerView.setAdapter(dinnerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DinnerRecipesActivity.this));

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
            Intent intent = new Intent(DinnerRecipesActivity.this, AddDinnerActivity.class);
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
        newDinnerId.clear();
        dinnerTitle.clear();
        hours.clear();
        minutes.clear();
        ingredients.clear();
        procedures.clear();

        Cursor cursor = databaseHelper.readAllDinnerRecipes();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                newDinnerId.add(cursor.getString(0));  // ID
                dinnerTitle.add(cursor.getString(1));  // Title
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
            dinnerAdapter.notifyDataSetChanged();
        }
    }
}