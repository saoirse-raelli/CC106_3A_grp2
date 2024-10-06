package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {

    private EditText editNoteTitle;
    private EditText editNoteStartTime;
    private EditText editNoteEndTime;
    private EditText editNoteIngredients;
    private EditText editNoteProcedures;
    private Button buttonSaveNote;
    private int cardIndex; // Declare cardIndex at class level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editNoteTitle = findViewById(R.id.edit_note_title);
        editNoteStartTime = findViewById(R.id.edit_note_start_time);
        editNoteEndTime = findViewById(R.id.edit_note_end_time);
        editNoteIngredients = findViewById(R.id.edit_note_ingredients);
        editNoteProcedures = findViewById(R.id.edit_note_procedures);
        buttonSaveNote = findViewById(R.id.button_save_note);

        // Retrieve existing note data if available
        Intent intent = getIntent();
        if (intent != null) {
            // Populate the EditTexts with existing data
            editNoteTitle.setText(intent.getStringExtra("noteTitle"));
            editNoteStartTime.setText(intent.getStringExtra("startTime"));
            editNoteEndTime.setText(intent.getStringExtra("endTime"));
            editNoteIngredients.setText(intent.getStringExtra("ingredients"));
            editNoteProcedures.setText(intent.getStringExtra("procedures"));
            cardIndex = intent.getIntExtra("cardIndex", -1); // Get the card index
        } else {
            // If there's no intent data, initialize cardIndex to -1
            cardIndex = -1;
        }

        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("noteTitle", editNoteTitle.getText().toString());
                resultIntent.putExtra("startTime", editNoteStartTime.getText().toString());
                resultIntent.putExtra("endTime", editNoteEndTime.getText().toString());
                resultIntent.putExtra("ingredients", editNoteIngredients.getText().toString());
                resultIntent.putExtra("procedures", editNoteProcedures.getText().toString());
                resultIntent.putExtra("cardIndex", cardIndex); // Pass back the index
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
