package com.example.prepmate;

import androidx.annotation.Nullable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database and table information
    private final Context context;
    private static final String DATABASE_NAME = "PrepMate.db";
    private static final int DATABASE_VERSION = 5;

    // Table names
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_NOTES = "Notes";
    private static final String TABLE_BREAKFAST = "Breakfast";
    private static final String TABLE_LUNCH = "Lunch";
    private static final String TABLE_DINNER = "Dinner";
    private static final String TABLE_SNACKS = "Snacks";
    private static final String TABLE_MIDNIGHT_SNACKS = "MidnightSnacks";

    // Column names for Users table
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_LASTNAME = "lastname";
    public static final String COLUMN_USER_FIRSTNAME = "firstname";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";

    // Column names for Notes table.... dedelete to soon
    public static final String COLUMN_NOTE_ID = "note_id";
    public static final String COLUMN_NOTE_TITLE = "title";
    public static final String COLUMN_NOTE_HOURS = "hours";
    public static final String COLUMN_NOTE_MINUTES = "minutes";
    public static final String COLUMN_NOTE_INGREDIENTS = "ingredients";
    public static final String COLUMN_NOTE_PROCEDURES = "procedures";

    // Column names for Breakfast table
    public static final String COLUMN_BREAKFAST_ID = "breakfast_id";
    public static final String COLUMN_BREAKFAST_TITLE = "title";
    public static final String COLUMN_BREAKFAST_HOURS = "hours";
    public static final String COLUMN_BREAKFAST_MINUTES = "minutes";
    public static final String COLUMN_BREAKFAST_INGREDIENTS = "ingredients";
    public static final String COLUMN_BREAKFAST_PROCEDURES = "procedures";

    // Column names for Lunch table
    public static final String COLUMN_LUNCH_ID = "lunch_id";
    public static final String COLUMN_LUNCH_TITLE = "title";
    public static final String COLUMN_LUNCH_HOURS = "hours";
    public static final String COLUMN_LUNCH_MINUTES = "minutes";
    public static final String COLUMN_LUNCH_INGREDIENTS = "ingredients";
    public static final String COLUMN_LUNCH_PROCEDURES = "procedures";

    // Column names for Dinner table
    public static final String COLUMN_DINNER_ID = "dinner_id";
    public static final String COLUMN_DINNER_TITLE = "title";
    public static final String COLUMN_DINNER_HOURS = "hours";
    public static final String COLUMN_DINNER_MINUTES = "minutes";
    public static final String COLUMN_DINNER_INGREDIENTS = "ingredients";
    public static final String COLUMN_DINNER_PROCEDURES = "procedures";


    // Column names for Snacks table
    public static final String COLUMN_SNACKS_ID = "snacks_id";
    public static final String COLUMN_SNACKS_TITLE = "title";
    public static final String COLUMN_SNACKS_HOURS = "hours";
    public static final String COLUMN_SNACKS_MINUTES = "minutes";
    public static final String COLUMN_SNACKS_INGREDIENTS = "ingredients";
    public static final String COLUMN_SNACKS_PROCEDURES = "procedures";

    // Column names for Midnight Snacks table
    public static final String COLUMN_MIDNIGHT_SNACKS_ID = "midnightsnacks_id";
    public static final String COLUMN_MIDNIGHT_SNACKS_TITLE = "title";
    public static final String COLUMN_MIDNIGHT_SNACKS_HOURS = "hours";
    public static final String COLUMN_MIDNIGHT_SNACKS_MINUTES = "minutes";
    public static final String COLUMN_MIDNIGHT_SNACKS_INGREDIENTS = "ingredients";
    public static final String COLUMN_MIDNIGHT_SNACKS_PROCEDURES = "procedures";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //1.  Create Users table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_LASTNAME + " TEXT, "
                + COLUMN_USER_FIRSTNAME + " TEXT, "
                + COLUMN_USER_USERNAME + " TEXT UNIQUE, "
                + COLUMN_USER_PASSWORD + " TEXT);";
        db.execSQL(createUsersTable);


        //2. Create Notes table
        String createNotesTable = "CREATE TABLE " + TABLE_NOTES + " ("
                + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOTE_TITLE + " TEXT, "
                + COLUMN_NOTE_HOURS + " TEXT, "
                + COLUMN_NOTE_MINUTES + " TEXT, "
                + COLUMN_NOTE_INGREDIENTS + " TEXT, "
                + COLUMN_NOTE_PROCEDURES + " TEXT, "
                + "category TEXT DEFAULT 'Customized');"; // Adding the new category column with a default value
        db.execSQL(createNotesTable);

        //2. Create Breakfast table
        String createBreakfastTable = "CREATE TABLE " + TABLE_BREAKFAST + " ("
                + COLUMN_BREAKFAST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BREAKFAST_TITLE + " TEXT, "
                + COLUMN_BREAKFAST_HOURS + " TEXT, "
                + COLUMN_BREAKFAST_MINUTES + " TEXT, "
                + COLUMN_BREAKFAST_INGREDIENTS + " TEXT, "
                + COLUMN_BREAKFAST_PROCEDURES + " TEXT, "
                + "category TEXT DEFAULT 'Breakfast');";
        db.execSQL(createBreakfastTable);

        //3. Create Lunch table
        String createLunchTable = "CREATE TABLE " + TABLE_LUNCH + " ("
                + COLUMN_LUNCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_LUNCH_TITLE + " TEXT, "
                + COLUMN_LUNCH_HOURS + " TEXT, "
                + COLUMN_LUNCH_MINUTES + " TEXT, "
                + COLUMN_LUNCH_INGREDIENTS + " TEXT, "
                + COLUMN_LUNCH_PROCEDURES + " TEXT, "
                + "category TEXT DEFAULT 'Lunch');";
        db.execSQL(createLunchTable);

        //4. Create Dinner table
        String createDinnerTable = "CREATE TABLE " + TABLE_DINNER + " ("
                + COLUMN_DINNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DINNER_TITLE + " TEXT, "
                + COLUMN_DINNER_HOURS + " TEXT, "
                + COLUMN_DINNER_MINUTES + " TEXT, "
                + COLUMN_DINNER_INGREDIENTS + " TEXT, "
                + COLUMN_DINNER_PROCEDURES + " TEXT, "
                + "category TEXT DEFAULT 'Dinner');";
        db.execSQL(createDinnerTable);

        //5. Create Snacks table
        String createSnacksTable = "CREATE TABLE " + TABLE_SNACKS + " ("
                + COLUMN_SNACKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SNACKS_TITLE + " TEXT, "
                + COLUMN_SNACKS_HOURS + " TEXT, "
                + COLUMN_SNACKS_MINUTES + " TEXT, "
                + COLUMN_SNACKS_INGREDIENTS + " TEXT, "
                + COLUMN_SNACKS_PROCEDURES + " TEXT, "
                + "category TEXT DEFAULT 'Snacks');";
        db.execSQL(createSnacksTable);

        //6. Create Midnight Snacks table
        String createMidnightSnacksTable = "CREATE TABLE " + TABLE_MIDNIGHT_SNACKS + " ("
                + COLUMN_MIDNIGHT_SNACKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_MIDNIGHT_SNACKS_TITLE + " TEXT, "
                + COLUMN_MIDNIGHT_SNACKS_HOURS + " TEXT, "
                + COLUMN_MIDNIGHT_SNACKS_MINUTES + " TEXT, "
                + COLUMN_MIDNIGHT_SNACKS_INGREDIENTS + " TEXT, "
                + COLUMN_MIDNIGHT_SNACKS_PROCEDURES + " TEXT, "
                + "category TEXT DEFAULT 'Midnight Snacks');";
        db.execSQL(createMidnightSnacksTable);



    }

    //METHODS FOR CREATING ACCOUNT
        //A.  SIGN IN
    public void addUser(String lastname, String firstname, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_LASTNAME, lastname);
        values.put(COLUMN_USER_FIRSTNAME, firstname);
        values.put(COLUMN_USER_USERNAME, username);
        values.put(COLUMN_USER_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        if (result == -1) {
            Log.e("DatabaseHelper", "Failed to add user: " + username);
        } else {
            Log.d("DatabaseHelper", "User added successfully: " + username);
        }
        db.close();
    }

    // Method to check user credentials
        //B.  LOGIN
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_USERNAME + " = ? AND "
                        + COLUMN_USER_PASSWORD + " = ?",
                new String[]{username, password}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
//method ends here



//METHODS FOR RECIPES
    //1
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table if it exists and create a new one
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);   //old version

        if (oldVersion < 2) {  // Check if the version is less than 2 (to handle migration)
            // Add the category column to each table if it does not already exist
            db.execSQL("ALTER TABLE " + TABLE_NOTES + " ADD COLUMN category TEXT DEFAULT 'Customized';");
            db.execSQL("ALTER TABLE " + TABLE_BREAKFAST + " ADD COLUMN category TEXT DEFAULT 'Breakfast';");
            db.execSQL("ALTER TABLE " + TABLE_LUNCH + " ADD COLUMN category TEXT DEFAULT 'Lunch';");
            db.execSQL("ALTER TABLE " + TABLE_DINNER + " ADD COLUMN category TEXT DEFAULT 'Dinner';");
            db.execSQL("ALTER TABLE " + TABLE_SNACKS + " ADD COLUMN category TEXT DEFAULT 'Snacks';");
            db.execSQL("ALTER TABLE " + TABLE_MIDNIGHT_SNACKS + " ADD COLUMN category TEXT DEFAULT 'Midnight Snacks';");
        }

        //onCreate(db);
    }


    //2
    // Adding a new card into the database
    public void addBook(String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOTE_TITLE, title);
        values.put(COLUMN_NOTE_HOURS, hours);
        values.put(COLUMN_NOTE_MINUTES, minutes);
        values.put(COLUMN_NOTE_INGREDIENTS, ingredients);
        values.put("category", "Customized");

        long result = db.insert(TABLE_NOTES, null, values);
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }
    }

    //2
    // Adding a new breakfast recipe into the database
    public void addBreakfastRecipe(String title, String hours, String minutes, String ingredients, String procedures) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

            values.put(COLUMN_BREAKFAST_TITLE, title);
            values.put(COLUMN_BREAKFAST_HOURS, hours);
            values.put(COLUMN_BREAKFAST_MINUTES, minutes);
            values.put(COLUMN_BREAKFAST_INGREDIENTS, ingredients);
            values.put(COLUMN_BREAKFAST_PROCEDURES, procedures);
            values.put("category", "Breakfast");

        long result = db.insert(TABLE_BREAKFAST, null, values);
            if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }
    }
    //3
    // Adding a new lunch recipe into the database
    public void addLunchRecipe(String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_LUNCH_TITLE, title);
        values.put(COLUMN_LUNCH_HOURS, hours);
        values.put(COLUMN_LUNCH_MINUTES, minutes);
        values.put(COLUMN_LUNCH_INGREDIENTS, ingredients);
        values.put(COLUMN_LUNCH_PROCEDURES, procedures);
        values.put("category", "Lunch");

        long result = db.insert(TABLE_LUNCH, null, values);
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }
    }

    //4
    // Adding a new dinner recipe into the database
    public void addDinnerRecipe(String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DINNER_TITLE, title);
        values.put(COLUMN_DINNER_HOURS, hours);
        values.put(COLUMN_DINNER_MINUTES, minutes);
        values.put(COLUMN_DINNER_INGREDIENTS, ingredients);
        values.put(COLUMN_DINNER_PROCEDURES, procedures);
        values.put("category", "Dinner");

        long result = db.insert(TABLE_DINNER, null, values);
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }
    }

    //5
    // Adding a new snacks recipe into the database
    public void addSnacksRecipe(String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_SNACKS_TITLE, title);
        values.put(COLUMN_SNACKS_HOURS, hours);
        values.put(COLUMN_SNACKS_MINUTES, minutes);
        values.put(COLUMN_SNACKS_INGREDIENTS, ingredients);
        values.put(COLUMN_SNACKS_PROCEDURES, procedures);
        values.put("category", "Snacks");

        long result = db.insert(TABLE_SNACKS, null, values);
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }
    }

    //6
    // Adding a new midnight snacks recipe into the database
    public void addMidnightSnacksRecipe(String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_MIDNIGHT_SNACKS_TITLE, title);
        values.put(COLUMN_MIDNIGHT_SNACKS_HOURS, hours);
        values.put(COLUMN_MIDNIGHT_SNACKS_MINUTES, minutes);
        values.put(COLUMN_MIDNIGHT_SNACKS_INGREDIENTS, ingredients);
        values.put(COLUMN_MIDNIGHT_SNACKS_PROCEDURES, procedures);
        values.put("category", "Midnight Snacks");

        long result = db.insert(TABLE_MIDNIGHT_SNACKS, null, values);
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }
    }




    //3
    // Reading all recipes from NOTES table
    public Cursor readAllData(){
        String createTable = "SELECT * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(createTable, null);

        }
        return cursor;
    }

    // Reading all recipes from BREAKFAST table
    public Cursor readAllBreakfastRecipes() {
        String createTable = "SELECT * FROM " + TABLE_BREAKFAST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(createTable, null);

        }
        return cursor;
    }

    // Reading all recipes from LUNCH table
    public Cursor readAllLunchRecipes() {
        String createTable = "SELECT * FROM " + TABLE_LUNCH;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(createTable, null);

        }
        return cursor;
    }


    // Reading all recipes from DINNER table
    public Cursor readAllDinnerRecipes() {
        String createTable = "SELECT * FROM " + TABLE_DINNER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(createTable, null);

        }
        return cursor;
    }

    // Reading all recipes from SNACKS table
    public Cursor readAllSnacksRecipes() {
        String createTable = "SELECT * FROM " + TABLE_SNACKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(createTable, null);

        }
        return cursor;
    }

    // Reading all recipes from MIDNIGHT SNACKS table
    public Cursor readAllMidnightSnacksRecipes() {
        String createTable = "SELECT * FROM " + TABLE_MIDNIGHT_SNACKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(createTable, null);

        }
        return cursor;
    }


    //4
    // Method to Update/Edit an existing NOTE RECIPE
    public void updateData(String row_id, String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, title);
        values.put(COLUMN_NOTE_HOURS, hours);
        values.put(COLUMN_NOTE_MINUTES, minutes);
        values.put(COLUMN_NOTE_INGREDIENTS, ingredients);
        values.put(COLUMN_NOTE_PROCEDURES, procedures);

        long result = db.update(TABLE_NOTES, values, "note_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to Update/Edit an existing BREAKFAST RECIPE
    public void updateBreakfastRecipe(String row_id, String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BREAKFAST_TITLE, title);
        values.put(COLUMN_BREAKFAST_HOURS, hours);
        values.put(COLUMN_BREAKFAST_MINUTES, minutes);
        values.put(COLUMN_BREAKFAST_INGREDIENTS, ingredients);
        values.put(COLUMN_BREAKFAST_PROCEDURES, procedures);

        long result = db.update(TABLE_BREAKFAST, values, "breakfast_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }

    }

    // Method to Update/Edit an existing LUNCH RECIPE
    public void updateLunchRecipe(String row_id, String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LUNCH_TITLE, title);
        values.put(COLUMN_LUNCH_HOURS, hours);
        values.put(COLUMN_LUNCH_MINUTES, minutes);
        values.put(COLUMN_LUNCH_INGREDIENTS, ingredients);
        values.put(COLUMN_LUNCH_PROCEDURES, procedures);

        long result = db.update(TABLE_LUNCH, values, "lunch_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }
    // Method to Update/Edit an existing DINNER RECIPE
    public void updateDinnerRecipe(String row_id, String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DINNER_TITLE, title);
        values.put(COLUMN_DINNER_HOURS, hours);
        values.put(COLUMN_DINNER_MINUTES, minutes);
        values.put(COLUMN_DINNER_INGREDIENTS, ingredients);
        values.put(COLUMN_DINNER_PROCEDURES, procedures);

        long result = db.update(TABLE_DINNER, values, "dinner_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to Update/Edit an existing SNACKS RECIPE
    public void updateSnacksRecipe(String row_id, String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SNACKS_TITLE, title);
        values.put(COLUMN_SNACKS_HOURS, hours);
        values.put(COLUMN_SNACKS_MINUTES, minutes);
        values.put(COLUMN_SNACKS_INGREDIENTS, ingredients);
        values.put(COLUMN_SNACKS_PROCEDURES, procedures);

        long result = db.update(TABLE_SNACKS, values, "snacks_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to Update/Edit an existing MIDNIGHT SNACKS RECIPE
    public void updateMidnightSnacksRecipe(String row_id, String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MIDNIGHT_SNACKS_TITLE, title);
        values.put(COLUMN_MIDNIGHT_SNACKS_HOURS, hours);
        values.put(COLUMN_MIDNIGHT_SNACKS_MINUTES, minutes);
        values.put(COLUMN_MIDNIGHT_SNACKS_INGREDIENTS, ingredients);
        values.put(COLUMN_MIDNIGHT_SNACKS_PROCEDURES, procedures);

        long result = db.update(TABLE_MIDNIGHT_SNACKS, values, "midnightsnacks_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }



    //5
    //Method to delete a NOTES Recipe
    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NOTES, "note_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }


    //Method to delete a BREAKFAST Recipe
    public void deleteBreakfastRecipe(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_BREAKFAST, "breakfast_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }


    //Method to delete a LUNCH Recipe
    public void deleteLunchRecipe(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_LUNCH, "lunch_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }


    //Method to delete a DINNER Recipe
    public void deleteDinnerRecipe(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_DINNER, "dinner_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    //Method to delete a SNACKS Recipe
    public void deleteSnacksRecipe(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_SNACKS, "snacks_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    //Method to delete a MIDNIGHT SNACKS Recipe
    public void deleteMidnightSnacksRecipe(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_MIDNIGHT_SNACKS, "midnightsnacks_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }






//di ko pa natatry: METHOD TO fetch recipes from all category tables and store them in a list

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query for each category
        Cursor breakfastCursor = db.rawQuery("SELECT title, hours, minutes FROM Breakfast", null);
        addRecipesToList(recipes, breakfastCursor, "Breakfast");

        Cursor lunchCursor = db.rawQuery("SELECT title, hours, minutes FROM Lunch", null);
        addRecipesToList(recipes, lunchCursor, "Lunch");

        Cursor dinnerCursor = db.rawQuery("SELECT title, hours, minutes FROM Dinner", null);
        addRecipesToList(recipes, dinnerCursor, "Dinner");

        Cursor snacksCursor = db.rawQuery("SELECT title, hours, minutes FROM Snacks", null);
        addRecipesToList(recipes, snacksCursor, "Snacks");

        Cursor midnightSnacksCursor = db.rawQuery("SELECT title, hours, minutes FROM MidnightSnacks", null);
        addRecipesToList(recipes, midnightSnacksCursor, "Midnight Snacks");

        return recipes;
    }

    private void addRecipesToList(List<Recipe> recipes, Cursor cursor, String category) {
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                String hours = cursor.getString(1);
                String minutes = cursor.getString(2);
                recipes.add(new Recipe(title, hours, minutes, category));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }




//METHOD TO GET DETAILS FROM RANDOM RECIPE SUGGESTIONS

}
