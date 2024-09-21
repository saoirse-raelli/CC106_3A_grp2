package com.example.mealplanner;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BreakfastPage extends AppCompatActivity {

    private TextView titleTextView;
    private TextView contentTextView;
    private static final int EDIT_NOTE_REQUEST = 1;
    private String noteTitle = "Default Title";
    private String noteContent = "Default Content";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breakfast_page);

        titleTextView = findViewById(R.id.text_note_title);
        contentTextView = findViewById(R.id.text_note_content);
        Button editButton = findViewById(R.id.button_edit_note);

        updateNoteDisplay();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BreakfastPage.this, EditNoteActivity.class);
                intent.putExtra("title", noteTitle);
                intent.putExtra("content", noteContent);
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            noteTitle = data.getStringExtra("title");
            noteContent = data.getStringExtra("content");
            updateNoteDisplay();
        }
    }

    private void updateNoteDisplay() {
        titleTextView.setText(noteTitle);
        contentTextView.setText(noteContent);
    }
}