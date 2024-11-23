package com.example.prepmate.calendar;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prepmate.R;
import com.example.prepmate.RecipeCalendar;
import com.example.prepmate.calendar.breakfastcalendar.BreakfastCalendarActivity;
import com.example.prepmate.calendar.lunchcalendar.LunchCalendarActivity;
import com.example.prepmate.calendar.snackscalendar.SnacksCalendarActivity;
import com.example.prepmate.calendar.dinnercalendar.DinnerCalendarActivity;
import com.example.prepmate.calendar.midnightsnackscalendar.MidnightSnacksCalendarActivity;
import java.util.List;


public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.RecipeViewHolder> {
    private List<RecipeCalendar> recipes;


    public CalendarAdapter(List<RecipeCalendar> recipes) {
        this.recipes = recipes;
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        RecipeCalendar recipe = recipes.get(position);
        holder.titleTextView.setText(recipe.getTitle());
        holder.hoursTextView.setText(String.valueOf(recipe.getHours()));
        holder.minutesTextView.setText(String.valueOf(recipe.getMinutes()));
        holder.categoryTextView.setText(recipe.getCategory());


        // Handle "Add Meal" button click
        holder.addMealButton.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent;


            // Determine the category and start the corresponding activity
            switch (recipe.getCategory()) {
                case "Breakfast":
                    intent = new Intent(context, BreakfastCalendarActivity.class);
                    break;
                case "Lunch":
                    intent = new Intent(context, LunchCalendarActivity.class);
                    break;
                case "Snacks":
                    intent = new Intent(context, SnacksCalendarActivity.class);
                    break;
                case "Dinner":
                    intent = new Intent(context, DinnerCalendarActivity.class);
                    break;
                case "Midnight Snacks":
                    intent = new Intent(context, MidnightSnacksCalendarActivity.class);
                    break;
                default:
                    intent = new Intent(context, CalendarActivity.class); // Fallback activity
                    break;
            }


            intent.putExtra("recipeId", recipe.getId()); // Pass the recipe ID
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }


    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView hoursTextView;
        TextView minutesTextView;
        TextView categoryTextView;
        Button addMealButton;


        RecipeViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.mealTypeTextView);
            hoursTextView = itemView.findViewById(R.id.hours);
            minutesTextView = itemView.findViewById(R.id.minutes);
            categoryTextView = itemView.findViewById(R.id.category);
            addMealButton = itemView.findViewById(R.id.addMealButton);
        }
    }
}


