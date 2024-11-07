package com.example.recipegenerator;

import android.content.Context;


import com.example.recipegenerator.Listeners.InstructionsListener;
import com.example.recipegenerator.Listeners.RandomRecipeResponseListener;
import com.example.recipegenerator.Models.InstructionsResponse;
import com.example.recipegenerator.Models.RandomRecipeApiResponse;
import com.example.recipegenerator.Models.RecipeDetailsResponse;
import com.example.recipegenerator.Listeners.RecipeDetailsListener;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {

    Context context;

    // Retrofit instance for making HTTP requests, configured with base URL and GSON converter
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/") // Base URL of the API
            .addConverterFactory(GsonConverterFactory.create()) // Converter to parse JSON responses
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags){
        // Creates an instance of the CallRandomRecipes interface for API calls
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);

        // Prepares a call to fetch random recipes, using the API key from resources and requesting 10 recipes
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipes(context.getString(R.string.api_key), "10", tags);

        // Asynchronously sends the request and receives the response
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable throwable) {
                listener.didError(throwable.getMessage());
            }
        });
    }

//Gets the information of the Title,
    public void getRecipeDetails(RecipeDetailsListener listener, int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable throwable) {
                listener.didError(throwable.getMessage());

            }
        });
    }


    public void getInstructions(InstructionsListener listener, int id){
        CallInstructions callInstructions = retrofit.create(CallInstructions.class);
        Call<List<InstructionsResponse>> call = callInstructions.callInstructions(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<List<InstructionsResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionsResponse>> call, Response<List<InstructionsResponse>> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<InstructionsResponse>> call, Throwable throwable) {
                listener.didError(throwable.getMessage());
            }
        });
    }


    private interface CallRandomRecipes {
        // GET request to the "recipes/random" endpoint, with parameters for the API key and number of recipes
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipes(
                @Query("apiKey") String apiKey, // API key as a query parameter
                @Query("number") String number,  // Number of recipes to fetch
                @Query("include-tags")List<String> tags
        );
    }

    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallInstructions{
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<InstructionsResponse>> callInstructions(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

}
