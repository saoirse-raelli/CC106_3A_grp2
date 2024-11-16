package com.example.prepmate.home.customrecipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prepmate.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private final Context context;
    private final Activity activity;
    private final ArrayList<String> newNoteId, noteTitle, hours, minutes, ingredients, procedures;

    // Constructor updated to include ingredients and procedures lists
    CustomAdapter(Activity activity, Context context,
                  ArrayList<String> newNoteId,
                  ArrayList<String> noteTitle,
                  ArrayList<String> hours,
                  ArrayList<String> minutes,
                  ArrayList<String> ingredients,
                  ArrayList<String> procedures) {
        this.activity = activity;
        this.context = context;
        this.newNoteId = newNoteId;
        this.noteTitle = noteTitle;
        this.hours = hours;
        this.minutes = minutes;
        this.ingredients = ingredients;
        this.procedures = procedures;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.noteTitle_txt.setText(noteTitle.get(position));
        holder.hours_txt.setText(hours.get(position));
        holder.minutes_txt.setText(minutes.get(position));

        // OnClickListener to open UpdateActivity with all data
        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", newNoteId.get(position));  // Pass the ID
            intent.putExtra("title", noteTitle.get(position));
            intent.putExtra("hours", hours.get(position));
            intent.putExtra("minutes", minutes.get(position));
            intent.putExtra("ingredients", ingredients.get(position));
            intent.putExtra("procedures", procedures.get(position));

            // Start UpdateActivity
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return newNoteId.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle_txt, hours_txt, minutes_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle_txt = itemView.findViewById(R.id.title);
            hours_txt = itemView.findViewById(R.id.hours);
            minutes_txt = itemView.findViewById(R.id.minutes);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
