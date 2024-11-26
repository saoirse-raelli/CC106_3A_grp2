package com.example.prepmate;


public class Today {
    private int id;
    private String title;
    private int hours;
    private int minutes;
    private String category;
    private int userId;
    private String  procedures;
    private String ingredients;


    public Today(int id, String title, int hours, int minutes, String category, int userId, String ingredients, String procedures) {
        this.id = id;
        this.title = title;
        this.hours = hours;
        this.minutes = minutes;
        this.category = category;
        this.userId = userId;
        this.procedures = procedures;
        this.ingredients = ingredients;
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

    public String getProcedures(){
        return procedures;
    }

    public String getIngredients(){
        return ingredients;
    }
}


