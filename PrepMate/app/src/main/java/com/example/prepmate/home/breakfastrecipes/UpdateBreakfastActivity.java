package com.example.prepmate.home.breakfastrecipes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.R;

public class UpdateBreakfastActivity extends AppCompatActivity {

    EditText title_input, ingredients_input, procedures_input;
    Spinner hours_input, minutes_input;
    Button update_button, delete_button;
    String id, title, hours, minutes, ingredients, procedures;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_breakfast);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        title_input = findViewById(R.id.title_input2);
        hours_input = findViewById(R.id.spinner_hours2);
        minutes_input = findViewById(R.id.spinner_minutes2);
        ingredients_input = findViewById(R.id.ingredients_input2);
        procedures_input = findViewById(R.id.procedures_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);


        userId = getLoggedInUserId();


        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }



        ArrayAdapter<CharSequence> hoursAdapter = ArrayAdapter.createFromResource(
                this, R.array.hours_array, android.R.layout.simple_spinner_item);
        hoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hours_input.setAdapter(hoursAdapter);

        ArrayAdapter<CharSequence> minutesAdapter = ArrayAdapter.createFromResource(
                this, R.array.minutes_array, android.R.layout.simple_spinner_item);
        minutesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutes_input.setAdapter(minutesAdapter);

        getAndSetIntentData();


        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateBreakfastActivity.this);
                title = title_input.getText().toString().trim();
                hours = hours_input.getSelectedItem().toString();
                minutes = minutes_input.getSelectedItem().toString();
                ingredients = ingredients_input.getText().toString().trim();
                procedures = procedures_input.getText().toString().trim();

                databaseHelper.updateBreakfastRecipe(id, userId, title, hours, minutes, ingredients, procedures);

                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("hours") && getIntent().hasExtra("minutes") &&
                getIntent().hasExtra("ingredients") && getIntent().hasExtra("procedures")){
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            hours = getIntent().getStringExtra("hours");
            minutes = getIntent().getStringExtra("minutes");
            ingredients = getIntent().getStringExtra("ingredients");
            procedures = getIntent().getStringExtra("procedures");


            title_input.setText(title);
            hours_input.setSelection(getIndex(hours_input, hours));
            minutes_input.setSelection(getIndex(minutes_input, minutes));
            ingredients_input.setText(ingredients);
            procedures_input.setText(procedures);


        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }


    private int getIndex(Spinner spinner, String value) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        return adapter.getPosition(value);
    }


    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateBreakfastActivity.this);

                databaseHelper.deleteBreakfastRecipe(id, userId);

                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);

                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Or use finish() to close the activity
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1);
    }

}