package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class BookBreakfast extends AppCompatActivity {

    private Button btnBack;
    private LinearLayout linearLayout;
    private Button addCardButton;
    private static final int EDIT_NOTE_REQUEST = 1;

    // List to hold card views
    private List<CardView> cardViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breakfast_book);

        linearLayout = findViewById(R.id.linearLayout);
        addCardButton = findViewById(R.id.addCardButton);
        btnBack = findViewById(R.id.btnBack);

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookBreakfast.this, EditNoteActivity.class);
                // Start editing with no pre-filled data
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        // Set onClickListener for the back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomePage();
            }
        });
    }

    private void openHomePage() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
        finish(); // Optional: close this activity
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            String noteTitle = data.getStringExtra("noteTitle");
            String startTime = data.getStringExtra("startTime");
            String endTime = data.getStringExtra("endTime");
            String ingredients = data.getStringExtra("ingredients");
            String procedures = data.getStringExtra("procedures");
            int cardIndex = data.getIntExtra("cardIndex", -1); // Get the index of the card being edited

            if (cardIndex != -1) {
                // Update the existing card
                updateCard(cardIndex, noteTitle, startTime, endTime, ingredients, procedures);
            } else {
                // Create a new card if cardIndex is -1
                addCard(noteTitle, startTime, endTime, ingredients, procedures);
            }
        }
    }

    private void updateCard(int cardIndex, String title, String startTime, String endTime, String ingredients, String procedures) {
        CardView cardView = cardViews.get(cardIndex);

        // Assuming you have TextViews inside the card to update
        ((TextView) ((LinearLayout) cardView.getChildAt(0)).getChildAt(0)).setText(title);
        ((TextView) ((LinearLayout) cardView.getChildAt(0)).getChildAt(1)).setText("Preparation Time: " + startTime + " to " + endTime);
        ((TextView) ((LinearLayout) cardView.getChildAt(0)).getChildAt(2)).setText("Ingredients: " + ingredients);
        ((TextView) ((LinearLayout) cardView.getChildAt(0)).getChildAt(3)).setText("Procedures: " + procedures);
    }

    private void addCard(String title, String startTime, String endTime, String ingredients, String procedures) {
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        cardLayoutParams.setMargins(0, 0, 0, 16);
        cardView.setLayoutParams(cardLayoutParams);
        cardView.setCardElevation(8);
        cardView.setRadius(10);

        LinearLayout innerLayout = new LinearLayout(this);
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setPadding(16, 16, 16, 16);

        TextView titleTextView = new TextView(this);
        titleTextView.setText(title);
        titleTextView.setTextSize(18);
        titleTextView.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView timeTextView = new TextView(this);
        timeTextView.setText("Preparation Time: " + startTime + " to " + endTime);
        timeTextView.setTextSize(16);

        TextView ingredientsTextView = new TextView(this);
        ingredientsTextView.setText("Ingredients: " + ingredients);
        ingredientsTextView.setTextSize(16);

        TextView proceduresTextView = new TextView(this);
        proceduresTextView.setText("Procedures: " + procedures);
        proceduresTextView.setTextSize(16);

        Button editButton = new Button(this);
        editButton.setText("Edit Recipe");
        int cardIndex = cardViews.size(); // Use the size as the unique index
        cardViews.add(cardView); // Add the card to the list

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookBreakfast.this, EditNoteActivity.class);
                intent.putExtra("noteTitle", title);
                intent.putExtra("startTime", startTime);
                intent.putExtra("endTime", endTime);
                intent.putExtra("ingredients", ingredients);
                intent.putExtra("procedures", procedures);
                intent.putExtra("cardIndex", cardIndex); // Pass the index for editing
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        innerLayout.addView(titleTextView);
        innerLayout.addView(timeTextView);
        innerLayout.addView(ingredientsTextView);
        innerLayout.addView(proceduresTextView);
        innerLayout.addView(editButton);
        cardView.addView(innerLayout);
        linearLayout.addView(cardView);
    }
}
