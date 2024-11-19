package com.example.prepmate;

import java.util.List;

public class DetailedRecipe {
    private String title;
    private String hours;
    private String minutes;
    private String category;
    private List<String> ingredients;
    private List<String> procedures;

    public DetailedRecipe(String title, String hours, String minutes, String category, List<String> ingredients, List<String> procedures) {
        this.title = title;
        this.hours = hours;
        this.minutes = minutes;
        this.category = category;
        this.ingredients = ingredients;
        this.procedures = procedures;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public String getHours() { return hours; }
    public String getMinutes() { return minutes; }
    public String getCategory() { return category; }
    public List<String> getIngredients() { return ingredients; }
    public List<String> getProcedures() { return procedures; }
}
