package com.example.prepmate.home.breakfastrecipes;

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

public class BreakfastRecipesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    ArrayList<String> newBreakfastId, breakfastTitle, hours, minutes, ingredients, procedures;
    BreakfastAdapter breakfastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_recipes);

        recyclerView = findViewById(R.id.recyclerView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back icon

        databaseHelper = new DatabaseHelper(this);
        newBreakfastId = new ArrayList<>();
        breakfastTitle = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        ingredients = new ArrayList<>();
        procedures = new ArrayList<>();

        storeDataInArrays();

        breakfastAdapter = new BreakfastAdapter(BreakfastRecipesActivity.this, this, newBreakfastId, breakfastTitle, hours, minutes, ingredients, procedures);
        recyclerView.setAdapter(breakfastAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BreakfastRecipesActivity.this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_add_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_recipe) {
            Intent intent = new Intent(BreakfastRecipesActivity.this, AddBreakfastActivity.class);
            startActivityForResult(intent, 1);  // Pass the request code 1
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    void storeDataInArrays() {
        newBreakfastId.clear();
        breakfastTitle.clear();
        hours.clear();
        minutes.clear();
        ingredients.clear();
        procedures.clear();

        int userId = getLoggedInUserId();
        if (userId == -1) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = databaseHelper.readAllBreakfastRecipes(userId);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                newBreakfastId.add(cursor.getString(0));  // ID
                breakfastTitle.add(cursor.getString(1));  // Title
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
            breakfastAdapter.notifyDataSetChanged();
        }
    }

    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1);
    }

}