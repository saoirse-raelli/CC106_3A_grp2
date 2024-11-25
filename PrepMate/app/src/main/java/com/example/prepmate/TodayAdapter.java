package com.example.prepmate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prepmate.R;
import com.example.prepmate.RecipeCalendar;
import com.example.prepmate.calendar.breakfastcalendar.BreakfastCalendarActivity;
import com.example.prepmate.calendar.lunchcalendar.LunchCalendarActivity;
import com.example.prepmate.calendar.snackscalendar.SnacksCalendarActivity;
import com.example.prepmate.calendar.dinnercalendar.DinnerCalendarActivity;
import com.example.prepmate.calendar.midnightsnackscalendar.MidnightSnacksCalendarActivity;

import java.util.List;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.RecipeViewHolder> {
    private List<RecipeCalendar> recipes;

    public TodayAdapter(List<RecipeCalendar> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        RecipeCalendar recipe = recipes.get(position);

        // Set the text for each TextView
        holder.titleTextView.setText(recipe.getTitle());
        holder.hoursTextView.setText(String.valueOf(recipe.getHours()));
        holder.minutesTextView.setText(String.valueOf(recipe.getMinutes()));
        holder.categoryTextView.setText(recipe.getCategory());

        // Change the CardView color if the recipe is invalid (ID = -1)
        if (recipe.getId() == -1) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#D3D3D3")); // Set the CardView color to red
        } else {
            holder.cardView.setCardBackgroundColor(Color.GREEN); // Reset to transparent if valid
        }

        // Handle item click
        holder.itemView.setOnClickListener(view -> {
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
                    intent = new Intent(context, TodayActivity.class); // Fallback activity
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
        CardView cardView; // Reference to CardView

        RecipeViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.mealTypeTextView);
            hoursTextView = itemView.findViewById(R.id.hours);
            minutesTextView = itemView.findViewById(R.id.minutes);
            categoryTextView = itemView.findViewById(R.id.category);
            cardView = itemView.findViewById(R.id.cardView); // Initialize CardView
        }
    }
}
