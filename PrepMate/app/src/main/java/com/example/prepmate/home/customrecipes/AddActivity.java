package com.example.prepmate.home.customrecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.R;

public class AddActivity extends AppCompatActivity {
    EditText title_input, hours_input, minutes_input, ingredients_input, procedures_input;
    Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // This will show the back icon
        getSupportActionBar().setDisplayShowHomeEnabled(true); // This ensures the icon is shown

        title_input = findViewById(R.id.title_input);
        hours_input = findViewById(R.id.hours_input);
        minutes_input = findViewById(R.id.minutes_input);
        ingredients_input = findViewById(R.id.ingredients_input);
        procedures_input = findViewById(R.id.procedures_input);
        save_button = findViewById(R.id.save_button);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(AddActivity.this);
                databaseHelper.addBook(title_input.getText().toString().trim(),
                        hours_input.getText().toString().trim(),
                        minutes_input.getText().toString().trim(),
                        ingredients_input.getText().toString().trim(),
                        procedures_input.getText().toString().trim());

                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish(); // Close AddActivity and go back to CustomRecipesActivity

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the back navigation
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Or use finish() to close the activity
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}