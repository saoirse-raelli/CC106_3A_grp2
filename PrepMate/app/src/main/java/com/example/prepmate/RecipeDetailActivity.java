package com.example.prepmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView titleTextView = findViewById(R.id.recipeTitle);
        TextView categoryTextView = findViewById(R.id.recipeCategory);
        TextView hoursTextView = findViewById(R.id.recipeHours);
        TextView minutesTextView = findViewById(R.id.recipeMinutes);
        TextView ingredientsTextView = findViewById(R.id.recipeIngredients);
        TextView proceduresTextView = findViewById(R.id.recipeProcedures);

        Intent intent = getIntent();
        titleTextView.setText(intent.getStringExtra("title"));
        categoryTextView.setText(intent.getStringExtra("category"));
        hoursTextView.setText(intent.getStringExtra("hours"));
        minutesTextView.setText(intent.getStringExtra("minutes"));
        ingredientsTextView.setText(intent.getStringExtra("ingredients"));
        proceduresTextView.setText(intent.getStringExtra("procedures"));

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}