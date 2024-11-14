package com.example.prepmate.home.customrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.R;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, hours_input, minutes_input, ingredients_input, procedures_input;
    Button update_button, delete_button;
    String id, title, hours, minutes, ingredients, procedures;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // This will show the back icon
        getSupportActionBar().setDisplayShowHomeEnabled(true); // This ensures the icon is shown

        title_input = findViewById(R.id.title_input2);
        hours_input = findViewById(R.id.hours_input2);
        minutes_input = findViewById(R.id.minutes_input2);
        ingredients_input = findViewById(R.id.ingredients_input2);
        procedures_input = findViewById(R.id.procedures_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();
                hours = hours_input.getText().toString().trim();
                minutes = minutes_input.getText().toString().trim();
                ingredients = ingredients_input.getText().toString().trim();
                procedures = procedures_input.getText().toString().trim();
                databaseHelper.updateData(id, title, hours, minutes, ingredients, procedures);

                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);  // Sending back the result
                finish(); // Close this activity and return to the previous one
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
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            hours = getIntent().getStringExtra("hours");
            minutes = getIntent().getStringExtra("minutes");
            ingredients = getIntent().getStringExtra("ingredients");
            procedures = getIntent().getStringExtra("procedures");

            //Setting Intent Data
            title_input.setText(title);
            hours_input.setText(hours);
            minutes_input.setText(minutes);
            ingredients_input.setText(ingredients);
            procedures_input.setText(procedures);


        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateActivity.this);
                databaseHelper.deleteOneRow(id);

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
        // Handle the back navigation
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Or use finish() to close the activity
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

