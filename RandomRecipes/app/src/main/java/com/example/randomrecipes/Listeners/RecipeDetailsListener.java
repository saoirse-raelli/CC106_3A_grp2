package com.example.randomrecipes.Listeners;

import com.example.randomrecipes.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
