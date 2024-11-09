package com.example.prepmate.Listeners;

import com.example.prepmate.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    // Method to handle the successful API response
    // Takes in the response data and a message
    void didFetch(RandomRecipeApiResponse response, String message);

    // Method to handle an error from the API response
    // Takes in an error message
    void didError(String message);
}
