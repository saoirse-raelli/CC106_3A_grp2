package com.example.prepmate.calendar.snackscalendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.prepmate.R;
import com.example.prepmate.RecipeCalendar;
import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.calendar.CalendarActivity;


public class SnacksCalendarAdapter extends RecyclerView.Adapter<SnacksCalendarAdapter.SnacksViewHolder> {
    private List<RecipeCalendar> snacksRecipes;
    private String selectedDate;
    private Context context;
    private DatabaseHelper databaseHelper;
    private int userId; // Ensure this is properly passed and used

    public SnacksCalendarAdapter(List<RecipeCalendar> snacksRecipes, String selectedDate, Context context, int userId) {
        this.snacksRecipes = snacksRecipes;
        this.selectedDate = selectedDate;
        this.context = context;
        this.userId = userId; // Initialize userId correctly
        this.databaseHelper = new DatabaseHelper(context); // Initialize DatabaseHelper
    }

    @NonNull
    @Override
    public SnacksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new SnacksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SnacksViewHolder holder, int position) {
        RecipeCalendar recipe = snacksRecipes.get(position);
        holder.mealTypeTextView.setText(recipe.getTitle());
        holder.hoursTextView.setText(String.valueOf(recipe.getHours()));
        holder.minutesTextView.setText(String.valueOf(recipe.getMinutes()));
        holder.categoryTextView.setText("Snacks");

        holder.addMealButton.setOnClickListener(v -> {
            int snacksId = recipe.getId(); // Get the ID of the recipe

            // Log the userId to debug
            Log.d("SnacksCalendarAdapter", "Inserting recipe with userId: " + userId);

            // Update or insert the snacks ID for the selected date
            if (databaseHelper.isDateInCalendar(selectedDate)) {
                databaseHelper.updateCalendarEntry(selectedDate, snacksId, "Snacks", userId);
            } else {
                databaseHelper.insertCalendarEntry(selectedDate, snacksId, "Snacks", userId);
            }

            Toast.makeText(context, "Added " + recipe.getTitle() + " to calendar!", Toast.LENGTH_SHORT).show();

            // Redirect to CalendarActivity
            Intent intent = new Intent(context, CalendarActivity.class);
            intent.putExtra("SELECTED_DATE", selectedDate);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return snacksRecipes.size();
    }

    static class SnacksViewHolder extends RecyclerView.ViewHolder {
        TextView mealTypeTextView;
        TextView hoursTextView;
        TextView minutesTextView;
        TextView categoryTextView;
        Button addMealButton;

        SnacksViewHolder(View itemView) {
            super(itemView);
            mealTypeTextView = itemView.findViewById(R.id.mealTypeTextView);
            hoursTextView = itemView.findViewById(R.id.hours);
            minutesTextView = itemView.findViewById(R.id.minutes);
            categoryTextView = itemView.findViewById(R.id.category);
            addMealButton = itemView.findViewById(R.id.addMealButton);
        }
    }
}
