package com.example.prepmate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.RecipeViewHolder> {
    private List<Today> recipes;

    public TodayAdapter(List<Today> recipes) {
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
        Today recipe = recipes.get(position);
        holder.titleTextView.setText(recipe.getTitle());
        holder.hoursTextView.setText(String.valueOf(recipe.getHours()));
        holder.minutesTextView.setText(String.valueOf(recipe.getMinutes()));
        holder.categoryTextView.setText(recipe.getCategory());
        holder.ingredientsTextView.setText(recipe.getIngredients());
        holder.proceduresTextView.setText(recipe.getProcedures());
        
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
        TextView ingredientsTextView;
        TextView proceduresTextView;

        RecipeViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.mealTypeTextView);
            hoursTextView = itemView.findViewById(R.id.hours);
            minutesTextView = itemView.findViewById(R.id.minutes);
            categoryTextView = itemView.findViewById(R.id.category);
            ingredientsTextView = itemView.findViewById(R.id.ingredients);
            proceduresTextView = itemView.findViewById(R.id.procedures);
        }
    }
}
