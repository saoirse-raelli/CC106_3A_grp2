package com.example.prepmate.home.customrecipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import androidx.recyclerview.widget.RecyclerView;

import com.example.prepmate.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private final Context context;
    Activity activity;
    private ArrayList newNoteId, noteTitle, startTime, endTime, ingredients, procedures;


    CustomAdapter(Activity activity, Context context,
                  ArrayList newNoteId,
                  ArrayList noteTitle,
                  ArrayList startTime,
                  ArrayList endTime,
                  ArrayList ingredients,
                  ArrayList procedures){
        this.activity = activity;
        this.context = context;
        this.newNoteId = newNoteId;
        this.noteTitle = noteTitle;
        this.startTime = startTime;
        this.endTime = endTime;
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.newNoteId_txt.setText(String.valueOf(newNoteId.get(position)));
        holder.noteTitle_txt.setText(String.valueOf(noteTitle.get(position)));
        holder.startTime_txt.setText(String.valueOf(startTime.get(position)));
        holder.endTime_txt.setText(String.valueOf(endTime.get(position)));
        holder.ingredients_txt.setText(String.valueOf(ingredients.get(position)));
        holder.procedures_txt.setText(String.valueOf(procedures.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(newNoteId.get(position)));
                intent.putExtra("title", String.valueOf(noteTitle.get(position)));
                intent.putExtra("start", String.valueOf(startTime.get(position)));
                intent.putExtra("end", String.valueOf(endTime.get(position)));
                intent.putExtra("ingredients", String.valueOf(ingredients.get(position)));
                intent.putExtra("procedures", String.valueOf(procedures.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newNoteId.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView newNoteId_txt, noteTitle_txt, startTime_txt, endTime_txt, ingredients_txt, procedures_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            newNoteId_txt = itemView.findViewById(R.id.id);
            noteTitle_txt = itemView.findViewById(R.id.title);
            startTime_txt = itemView.findViewById(R.id.start_time);
            endTime_txt = itemView.findViewById(R.id.end_time);
            ingredients_txt = itemView.findViewById(R.id.ingredients);
            procedures_txt = itemView.findViewById(R.id.procedures);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
