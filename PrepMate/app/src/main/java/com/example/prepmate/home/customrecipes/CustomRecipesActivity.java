package com.example.prepmate.home.customrecipes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.R;

import java.util.ArrayList;

public class CustomRecipesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button addCardButton;
    DatabaseHelper databaseHelper;
    ArrayList<String> newNoteId, noteTitle, ingredients, procedures, startTime, endTime, type;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_recipes);
        recyclerView = findViewById(R.id.recyclerView);
        addCardButton = findViewById(R.id.addCardButton);
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomRecipesActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(this); // Initialize DatabaseHelper
        newNoteId = new ArrayList<>();
        noteTitle = new ArrayList<>();
        ingredients = new ArrayList<>();
        procedures = new ArrayList<>();
        startTime = new ArrayList<>();
        endTime = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(CustomRecipesActivity.this, this, newNoteId, noteTitle, ingredients, procedures, startTime, endTime);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CustomRecipesActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = databaseHelper.readAllData();
        if (cursor.getCount () == 0){
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT) .show();
        }else{
            while (cursor.moveToNext()){
                newNoteId.add(cursor.getString(0));
                noteTitle.add(cursor.getString(1));
                ingredients.add(cursor.getString(2));
                procedures.add(cursor.getString(3));
                startTime.add(cursor.getString(4));
                endTime.add(cursor.getString(5));
            }
        }
    }
}
