package com.example.prepmate;

public class Recipe {
    private String title;
    private String hours;
    private String minutes;
    private String category;
    private String ingredients;
    private String procedures;

    // Constructor
    public Recipe(String title, String hours, String minutes, String category, String ingredients, String procedures) {
        this.title = title;
        this.hours = hours;
        this.minutes = minutes;
        this.category = category;
        this.ingredients = ingredients;
        this.procedures = procedures;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getHours() { return hours; }
    public void setHours(String hours) { this.hours = hours; }

    public String getMinutes() { return minutes; }
    public void setMinutes(String minutes) { this.minutes = minutes; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getProcedures() { return procedures; }
    public void setProcedures(String procedures) { this.procedures = procedures; }
}

