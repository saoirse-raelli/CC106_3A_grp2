package com.example.prepmate.home.lunchrecipes;

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

public class LunchRecipesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    ArrayList<String> newLunchId, lunchTitle, hours, minutes, ingredients, procedures;
    LunchAdapter lunchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_recipes);

        recyclerView = findViewById(R.id.recyclerView);

        Toolbar toolbar = findViewById(R.id.lunch_recipes_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelper = new DatabaseHelper(this);
        newLunchId = new ArrayList<>();
        lunchTitle = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        ingredients = new ArrayList<>();
        procedures = new ArrayList<>();

        storeDataInArrays();

        lunchAdapter = new LunchAdapter(LunchRecipesActivity.this, this, newLunchId, lunchTitle, hours, minutes, ingredients, procedures);
        recyclerView.setAdapter(lunchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(LunchRecipesActivity.this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_add_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_recipe) {
            Intent intent = new Intent(LunchRecipesActivity.this, AddLunchActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void storeDataInArrays() {
        newLunchId.clear();
        lunchTitle.clear();
        hours.clear();
        minutes.clear();
        ingredients.clear();
        procedures.clear();

        int userId = getLoggedInUserId();
        if (userId == -1) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = databaseHelper.readAllLunchRecipes(userId);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                newLunchId.add(cursor.getString(0));
                lunchTitle.add(cursor.getString(1));
                hours.add(cursor.getString(2));
                minutes.add(cursor.getString(3));
                ingredients.add(cursor.getString(4));
                procedures.add(cursor.getString(5));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            storeDataInArrays();
            lunchAdapter.notifyDataSetChanged();
        }
    }
    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1);
    }

}