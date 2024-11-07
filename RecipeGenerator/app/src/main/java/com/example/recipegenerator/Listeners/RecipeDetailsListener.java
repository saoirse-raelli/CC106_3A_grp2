package com.example.recipegenerator.Listeners;
import com.example.recipegenerator.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
