package com.example.prepmate;


public class RecipeCalendar {
    private int id;
    private String title;
    private int hours;
    private int minutes;
    private String category;
    private int userId;


    public RecipeCalendar(int id, String title, int hours, int minutes, String category, int userId) {
        this.id = id;
        this.title = title;
        this.hours = hours;
        this.minutes = minutes;
        this.category = category;
        this.userId = userId;
    }

    public int getUserId() { return userId; }

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


