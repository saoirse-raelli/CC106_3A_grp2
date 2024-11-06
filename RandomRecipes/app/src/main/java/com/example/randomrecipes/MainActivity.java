package com.example.randomrecipes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.randomrecipes.Adapters.RandomRecipeAdapter;
import com.example.randomrecipes.Listeners.RandomRecipeResponseListener;
import com.example.randomrecipes.Listeners.RecipeClickListener;
import com.example.randomrecipes.Models.RandomRecipeApiResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    List<String> tags = new ArrayList<>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout for this activity

        // Initialize the ProgressDialog and set its title
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading ... ");

        searchView = findViewById(R.id.searchView_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                manager.getRandomRecipes(randomRecipeResponseListener, tags);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        spinner = findViewById(R.id.spinner_tags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );

        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        // Initialize the RequestManager to manage API calls
        manager = new RequestManager(this);

        // Start fetching random recipes and show the loading dialog
   //     manager.getRandomRecipes(randomRecipeResponseListener);
   //     dialog.show();
    }

    // Listener for handling the API response or error
    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {

        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            // Dismiss the loading dialog once data is fetched
            dialog.dismiss();

            // Initialize the RecyclerView, set fixed size for optimization, and layout manager for grid display
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true); // Optimizes RecyclerView performance
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1)); // Grid layout with 1 column

            // Initialize and set the adapter with the list of recipes from the API response
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this, response.recipes, recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter); // Set adapter to RecyclerView to display items
        }

        @Override
        public void didError(String message) {
            // Display an error message in a toast if fetching data fails
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());
            manager.getRandomRecipes(randomRecipeResponseListener, tags);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(MainActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));
        }
    };

}

