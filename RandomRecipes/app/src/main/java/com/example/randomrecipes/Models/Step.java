package com.example.randomrecipes.Models;

import android.health.connect.datatypes.units.Length;

import java.util.ArrayList;

public class Step {
    public int number;
    public String step;
    public ArrayList<Ingredient> ingredients;
    public ArrayList<Equipment> equipment;
    public Length length;
}
