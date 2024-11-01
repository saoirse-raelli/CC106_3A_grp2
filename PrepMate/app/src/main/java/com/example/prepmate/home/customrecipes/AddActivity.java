package com.example.prepmate.home.customrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.R;

public class AddActivity extends AppCompatActivity {
    EditText title_input, startTime_input, endTime_input, ingredients_input, procedures_input;
    Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_input = findViewById(R.id.title_input);
        startTime_input = findViewById(R.id.startTime_input);
        endTime_input = findViewById(R.id.endTime_input);
        ingredients_input = findViewById(R.id.ingredients_input);
        procedures_input = findViewById(R.id.procedures_input);
        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(AddActivity.this);
                databaseHelper.addBook(title_input.getText().toString().trim(),
                        startTime_input.getText().toString().trim(),
                        endTime_input.getText().toString().trim(),
                        ingredients_input.getText().toString().trim(),
                        procedures_input.getText().toString().trim());
            }
        });

    }
}