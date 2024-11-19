package com.example.prepmate;

public class Recipe {
    private String title;
    private String hours;
    private String minutes;
    private String category;

    public Recipe(String title, String hours, String minutes, String category) {
        this.title = title;
        this.hours = hours;
        this.minutes = minutes;
        this.category = category;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public String getHours() { return hours; }
    public String getMinutes() { return minutes; }
    public String getCategory() { return category; }
}

