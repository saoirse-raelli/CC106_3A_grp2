package com.example.prepmate.home.lunchrecipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prepmate.R;
import java.util.ArrayList;

public class LunchAdapter extends RecyclerView.Adapter<LunchAdapter.MyViewHolder> {

    private final Context context;
    private final Activity activity;
    private final ArrayList<String> newLunchId, lunchTitle, hours, minutes, ingredients, procedures;

    public LunchAdapter(Activity activity, Context context,
                            ArrayList<String> newLunchId,
                            ArrayList<String> lunchTitle,
                            ArrayList<String> hours,
                            ArrayList<String> minutes,
                            ArrayList<String> ingredients,
                            ArrayList<String> procedures) {
        this.activity = activity;
        this.context = context;
        this.newLunchId = newLunchId;
        this.lunchTitle = lunchTitle;
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
        holder.lunchTitle_txt.setText(lunchTitle.get(position));
        holder.hours_txt.setText(hours.get(position));
        holder.minutes_txt.setText(minutes.get(position));

        // OnClickListener to open UpdateActivity with all data
        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateLunchActivity.class);
            intent.putExtra("id", newLunchId.get(position));
            intent.putExtra("title", lunchTitle.get(position));
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
        return newLunchId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView lunchTitle_txt, hours_txt, minutes_txt;
        View mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the views
            lunchTitle_txt = itemView.findViewById(R.id.title);
            hours_txt = itemView.findViewById(R.id.hours);
            minutes_txt = itemView.findViewById(R.id.minutes);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
