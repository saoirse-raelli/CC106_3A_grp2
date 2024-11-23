package com.example.prepmate;


public class RecipeCalendar {
    private int id;
    private String title;
    private int hours;
    private int minutes;
    private String category;


    public RecipeCalendar(int id, String title, int hours, int minutes, String category) {
        this.id = id;
        this.title = title;
        this.hours = hours;
        this.minutes = minutes;
        this.category = category;
    }


    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public int getHours() {
        return hours;
    }


    public int getMinutes() {
        return minutes;
    }


    public String getCategory() {
        return category;
    }
}


