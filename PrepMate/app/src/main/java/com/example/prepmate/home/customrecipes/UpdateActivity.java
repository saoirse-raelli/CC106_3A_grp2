package com.example.prepmate.home.customrecipes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.R;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, startTime_input, endTime_input, ingredients_input, procedures_input;
    Button update_button, delete_button;
    String id, title, start, end, ingredients, procedures;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input2);
        startTime_input = findViewById(R.id.startTime_input2);
        endTime_input = findViewById(R.id.endTime_input2);
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
                start = startTime_input.getText().toString().trim();
                end = endTime_input.getText().toString().trim();
                ingredients = ingredients_input.getText().toString().trim();
                procedures = procedures_input.getText().toString().trim();
                databaseHelper.updateData(id, title, start, end, ingredients, procedures);
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
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("start") && getIntent().hasExtra("end") &&
                getIntent().hasExtra("ingredients") && getIntent().hasExtra("procedures")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            start = getIntent().getStringExtra("start");
            end = getIntent().getStringExtra("end");
            ingredients = getIntent().getStringExtra("ingredients");
            procedures = getIntent().getStringExtra("procedures");

            //Setting Intent Data
            title_input.setText(title);
            startTime_input.setText(start);
            endTime_input.setText(end);
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
}

