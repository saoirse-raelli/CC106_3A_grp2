package com.example.mealplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditNoteActivity extends Activity {

    private EditText titleEditText;
    private EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        titleEditText = findViewById(R.id.edit_note_title);
        contentEditText = findViewById(R.id.edit_note_content);

        Button saveButton = findViewById(R.id.button_save_note);

        Intent intent = getIntent();
        titleEditText.setText(intent.getStringExtra("title"));
        contentEditText.setText(intent.getStringExtra("content"));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", titleEditText.getText().toString());
                resultIntent.putExtra("content", contentEditText.getText().toString());
                setResult(RESULT_OK, resultIntent);

                Toast.makeText(EditNoteActivity.this, "Recipe Saved", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}
