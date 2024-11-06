package com.example.randomrecipes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.randomrecipes.Listeners.RecipeClickListener;
import com.example.randomrecipes.Models.Recipe;
import com.example.randomrecipes.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder> {

    Context context;
    List<Recipe> list;
    RecipeClickListener listener;

    // Constructor to initialize context and recipe list
    public RandomRecipeAdapter(Context context, List<Recipe> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView and create a new ViewHolder
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        // Bind data to the views in the ViewHolder for the current position in the list
        holder.textView_title.setText(list.get(position).title); // Set recipe title
        holder.textView_title.setSelected(true); // Enable marquee effect for long titles
        holder.textView_likes.setText(list.get(position).aggregateLikes + " Likes"); // Set like count
        holder.textView_servings.setText(list.get(position).servings + " Servings"); // Set serving count
        holder.textView_time.setText(list.get(position).readyInMinutes + " Minutes"); // Set preparation time

        // Load recipe image into the ImageView using Picasso library
        Picasso.get().load(list.get(position).image).into(holder.imageView_food);

        holder.random_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the list
        return list.size();
    }
}

// ViewHolder class for holding the views for each item in the RecyclerView
class RandomRecipeViewHolder extends RecyclerView.ViewHolder {
    // UI components for each recipe item
    CardView random_list_container; // CardView container for each recipe
    TextView textView_title, textView_servings, textView_likes, textView_time; // TextViews for displaying details
    ImageView imageView_food; // ImageView for displaying the recipe image

    // Constructor to initialize views by finding them by ID
    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        random_list_container = itemView.findViewById(R.id.random_list_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_servings = itemView.findViewById(R.id.textView_servings);
        textView_likes = itemView.findViewById(R.id.textView_likes);
        textView_time = itemView.findViewById(R.id.textView_time);
        imageView_food = itemView.findViewById(R.id.imageView_food);
    }
}
