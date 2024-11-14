package com.example.prepmate.home.breakfastrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prepmate.R;
import com.example.prepmate.home.customrecipes.AddActivity;
import com.example.prepmate.home.customrecipes.CustomRecipesActivity;

public class BreakfastRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_recipes);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.breakfast_recipes_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back icon

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
            Intent intent = new Intent(BreakfastRecipesActivity.this, AddBreakfastActivity.class);
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
}