package com.example.prepmate.home.lunchrecipes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.R;


public class AddLunchActivity extends AppCompatActivity {

    EditText title_input, ingredients_input, procedures_input;
    Spinner hours_input, minutes_input;
    Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_lunch);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        title_input = findViewById(R.id.title_input);
        hours_input = findViewById(R.id.spinner_hours);
        minutes_input = findViewById(R.id.spinner_minutes);
        ingredients_input = findViewById(R.id.ingredients_input);
        procedures_input = findViewById(R.id.procedures_input);
        save_button = findViewById(R.id.save_button);


        ArrayAdapter<CharSequence> hoursAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.hours_array,
                android.R.layout.simple_spinner_item
        );
        hoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hours_input.setAdapter(hoursAdapter);

        ArrayAdapter<CharSequence> minutesAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.minutes_array,
                android.R.layout.simple_spinner_item
        );
        minutesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutes_input.setAdapter(minutesAdapter);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int userId = getLoggedInUserId();

                DatabaseHelper databaseHelper = new DatabaseHelper(AddLunchActivity.this);

                String hours = hours_input.getSelectedItem().toString();
                String minutes = minutes_input.getSelectedItem().toString();

                databaseHelper.addLunchRecipe(
                        title_input.getText().toString().trim(),
                        hours,
                        minutes,
                        ingredients_input.getText().toString().trim(),
                        procedures_input.getText().toString().trim(),
                        userId
                );

                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int getLoggedInUserId() {

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1);
    }
}