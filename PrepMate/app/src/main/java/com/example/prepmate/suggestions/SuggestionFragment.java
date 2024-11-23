package com.example.prepmate.suggestions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prepmate.R;
import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.Recipe;
import com.example.prepmate.RecipeAdapter;
import com.example.prepmate.RecipeDetailActivity;

import java.util.List;

public class SuggestionFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;

    // Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public SuggestionFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     */
    public static SuggestionFragment newInstance(String param1, String param2) {
        SuggestionFragment fragment = new SuggestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggestion, container, false);

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.recipesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get logged-in user's ID from SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginPrefs", getContext().MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1); // Default value -1 if not logged in

        if (userId == -1) {
            Toast.makeText(getContext(), "User not logged in!", Toast.LENGTH_SHORT).show();
        } else {
            // Fetch recipes from the database for the logged-in user
            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
            recipeList = databaseHelper.getAllRecipes(userId);


            // Set up the adapter with click listener
            recipeAdapter = new RecipeAdapter(recipeList, recipe -> {
                // Navigate to RecipeDetailActivity
                Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
                intent.putExtra("title", recipe.getTitle());
                intent.putExtra("category", recipe.getCategory());
                intent.putExtra("hours", recipe.getHours());
                intent.putExtra("minutes", recipe.getMinutes());
                intent.putExtra("ingredients", recipe.getIngredients());
                intent.putExtra("procedures", recipe.getProcedures());
                startActivity(intent);
            });

            recyclerView.setAdapter(recipeAdapter);
        }

        return view;
    }
}
