package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BookBreakfast extends AppCompatActivity {

    private Button btnBack, btnAdd, button_edit_note;
    private TextView titleTextView;
    private TextView contentTextView;
    private static final int EDIT_NOTE_REQUEST = 1;
    private String noteTitle = "Recipe Name: ";
    private String noteContent = "Ingredients: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breakfast_book);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomePage();
            }
        });

        button_edit_note = findViewById(R.id.button_edit_note);
        button_edit_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditNoteActivity();
            }
        });

        titleTextView = findViewById(R.id.text_note_title);
        contentTextView = findViewById(R.id.text_note_content);

        updateNoteDisplay();
    }

    private void updateNoteDisplay() {
        titleTextView.setText(noteTitle);
        contentTextView.setText(noteContent);
    }

    public void openHomePage() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void openEditNoteActivity() {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("title", noteTitle);
        intent.putExtra("content", noteContent);
        startActivityForResult(intent, EDIT_NOTE_REQUEST);
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
}