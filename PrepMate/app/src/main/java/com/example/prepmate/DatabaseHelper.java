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
    public static final String TABLE_CALENDAR = "Calendar";

    // Column names for Users table
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_LASTNAME = "lastname";
    public static final String COLUMN_USER_FIRSTNAME = "firstname";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";

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

    // Column names for Calendar table
    public static final String COLUMN_CALENDAR_ID = "id";
    public static final String COLUMN_CALENDAR_SELECTED_DATE = "selected_date";



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


        //2. Create Breakfast table
        String createBreakfastTable = "CREATE TABLE " + TABLE_BREAKFAST + " ("
                + COLUMN_BREAKFAST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BREAKFAST_TITLE + " TEXT, "
                + COLUMN_BREAKFAST_HOURS + " TEXT, "
                + COLUMN_BREAKFAST_MINUTES + " TEXT, "
                + COLUMN_BREAKFAST_INGREDIENTS + " TEXT, "
                + COLUMN_BREAKFAST_PROCEDURES + " TEXT, "
                + "category TEXT DEFAULT 'Breakfast', "
                + "user_id INTEGER);";
        db.execSQL(createBreakfastTable);

        //3. Create Lunch table
        String createLunchTable = "CREATE TABLE " + TABLE_LUNCH + " ("
                + COLUMN_LUNCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_LUNCH_TITLE + " TEXT, "
                + COLUMN_LUNCH_HOURS + " TEXT, "
                + COLUMN_LUNCH_MINUTES + " TEXT, "
                + COLUMN_LUNCH_INGREDIENTS + " TEXT, "
                + COLUMN_LUNCH_PROCEDURES + " TEXT, "
                + "category TEXT DEFAULT 'Lunch', "
                + "user_id INTEGER);";
        db.execSQL(createLunchTable);

        //4. Create Dinner table
        String createDinnerTable = "CREATE TABLE " + TABLE_DINNER + " ("
                + COLUMN_DINNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DINNER_TITLE + " TEXT, "
                + COLUMN_DINNER_HOURS + " TEXT, "
                + COLUMN_DINNER_MINUTES + " TEXT, "
                + COLUMN_DINNER_INGREDIENTS + " TEXT, "
                + COLUMN_DINNER_PROCEDURES + " TEXT, "
                + "category TEXT DEFAULT 'Dinner', "
                + "user_id INTEGER);";
        db.execSQL(createDinnerTable);

        //5. Create Snacks table
        String createSnacksTable = "CREATE TABLE " + TABLE_SNACKS + " ("
                + COLUMN_SNACKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SNACKS_TITLE + " TEXT, "
                + COLUMN_SNACKS_HOURS + " TEXT, "
                + COLUMN_SNACKS_MINUTES + " TEXT, "
                + COLUMN_SNACKS_INGREDIENTS + " TEXT, "
                + COLUMN_SNACKS_PROCEDURES + " TEXT, "
                + "category TEXT DEFAULT 'Snacks', "
                + "user_id INTEGER);";
        db.execSQL(createSnacksTable);

        //6. Create Midnight Snacks table
        String createMidnightSnacksTable = "CREATE TABLE " + TABLE_MIDNIGHT_SNACKS + " ("
                + COLUMN_MIDNIGHT_SNACKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_MIDNIGHT_SNACKS_TITLE + " TEXT, "
                + COLUMN_MIDNIGHT_SNACKS_HOURS + " TEXT, "
                + COLUMN_MIDNIGHT_SNACKS_MINUTES + " TEXT, "
                + COLUMN_MIDNIGHT_SNACKS_INGREDIENTS + " TEXT, "
                + COLUMN_MIDNIGHT_SNACKS_PROCEDURES + " TEXT, "
                + "category TEXT DEFAULT 'Midnight Snacks', "
                + "user_id INTEGER);";
        db.execSQL(createMidnightSnacksTable);

        // Create Calendar table
        String createCalendarTable = "CREATE TABLE " + TABLE_CALENDAR + " ("
                + COLUMN_CALENDAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CALENDAR_SELECTED_DATE + " TEXT NOT NULL, "
                + "user_id INTEGER, "
                + "breakfast_id INTEGER, "
                + "lunch_id INTEGER, "
                + "snacks_id INTEGER, "
                + "dinner_id INTEGER, "
                + "midnightsnacks_id INTEGER, "
                + "FOREIGN KEY (breakfast_id) REFERENCES " + TABLE_BREAKFAST + "(" + COLUMN_BREAKFAST_ID + "), "
                + "FOREIGN KEY (lunch_id) REFERENCES " + TABLE_LUNCH + "(" + COLUMN_LUNCH_ID + "), "
                + "FOREIGN KEY (snacks_id) REFERENCES " + TABLE_SNACKS + "(" + COLUMN_SNACKS_ID + "), "
                + "FOREIGN KEY (dinner_id) REFERENCES " + TABLE_DINNER + "(" + COLUMN_DINNER_ID + "), "
                + "FOREIGN KEY (midnightsnacks_id) REFERENCES " + TABLE_MIDNIGHT_SNACKS + "(" + COLUMN_MIDNIGHT_SNACKS_ID + "));";
        db.execSQL(createCalendarTable);

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

    // Method to retrieve user_id based on username and password
        //C. GET USER ID
    public int getUserId(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1; // Default value if not found

        // Query to fetch the user_id
        Cursor cursor = db.rawQuery(
                "SELECT user_id FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_USERNAME + " = ? AND "
                        + COLUMN_USER_PASSWORD + " = ?",
                new String[]{username, password}
        );

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0); // Get the user_id from the first column
        }

        cursor.close();
        db.close();
        return userId;
    }

//method ends here



//METHODS FOR RECIPES
    //1
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 5) { // Assume version 4 introduces the category and user_id columns
            // Add category and user_id to Notes table
            db.execSQL("ALTER TABLE " + TABLE_NOTES + " ADD COLUMN category TEXT DEFAULT 'Customized';");
            db.execSQL("ALTER TABLE " + TABLE_NOTES + " ADD COLUMN user_id INTEGER;");

            // Add category and user_id to Breakfast table
            db.execSQL("ALTER TABLE " + TABLE_BREAKFAST + " ADD COLUMN category TEXT DEFAULT 'Breakfast';");
            db.execSQL("ALTER TABLE " + TABLE_BREAKFAST + " ADD COLUMN user_id INTEGER;");

            // Repeat for other tables (Lunch, Dinner, Snacks, Midnight Snacks)
            db.execSQL("ALTER TABLE " + TABLE_LUNCH + " ADD COLUMN category TEXT DEFAULT 'Lunch';");
            db.execSQL("ALTER TABLE " + TABLE_LUNCH + " ADD COLUMN user_id INTEGER;");

            db.execSQL("ALTER TABLE " + TABLE_DINNER + " ADD COLUMN category TEXT DEFAULT 'Dinner';");
            db.execSQL("ALTER TABLE " + TABLE_DINNER + " ADD COLUMN user_id INTEGER;");

            db.execSQL("ALTER TABLE " + TABLE_SNACKS + " ADD COLUMN category TEXT DEFAULT 'Snacks';");
            db.execSQL("ALTER TABLE " + TABLE_SNACKS + " ADD COLUMN user_id INTEGER;");

            db.execSQL("ALTER TABLE " + TABLE_MIDNIGHT_SNACKS + " ADD COLUMN category TEXT DEFAULT 'Midnight Snacks';");
            db.execSQL("ALTER TABLE " + TABLE_MIDNIGHT_SNACKS + " ADD COLUMN user_id INTEGER;");

                    // Create the Calendar table
                    String createCalendarTable = "CREATE TABLE " + TABLE_CALENDAR + " ("
                            + COLUMN_CALENDAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + COLUMN_CALENDAR_SELECTED_DATE + " TEXT NOT NULL, "
                            + "user_id INTEGER, "
                            + "breakfast_id INTEGER, "
                            + "lunch_id INTEGER, "
                            + "snacks_id INTEGER, "
                            + "dinner_id INTEGER, "
                            + "midnightsnacks_id INTEGER, "
                            + "FOREIGN KEY (breakfast_id) REFERENCES " + TABLE_BREAKFAST + "(" + COLUMN_BREAKFAST_ID + "), "
                            + "FOREIGN KEY (lunch_id) REFERENCES " + TABLE_LUNCH + "(" + COLUMN_LUNCH_ID + "), "
                            + "FOREIGN KEY (snacks_id) REFERENCES " + TABLE_SNACKS + "(" + COLUMN_SNACKS_ID + "), "
                            + "FOREIGN KEY (dinner_id) REFERENCES " + TABLE_DINNER + "(" + COLUMN_DINNER_ID + "), "
                            + "FOREIGN KEY (midnightsnacks_id) REFERENCES " + TABLE_MIDNIGHT_SNACKS + "(" + COLUMN_MIDNIGHT_SNACKS_ID + "));";
                    db.execSQL(createCalendarTable);
                }
    }





    //2
    // Adding a new breakfast recipe into the database
    public void addBreakfastRecipe(String title, String hours, String minutes, String ingredients, String procedures, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Add values to the columns
        values.put(COLUMN_BREAKFAST_TITLE, title);
        values.put(COLUMN_BREAKFAST_HOURS, hours);
        values.put(COLUMN_BREAKFAST_MINUTES, minutes);
        values.put(COLUMN_BREAKFAST_INGREDIENTS, ingredients);
        values.put(COLUMN_BREAKFAST_PROCEDURES, procedures);
        values.put("category", "Breakfast"); // Set the category
        values.put("user_id", userId);       // Set the user ID

        // Insert the data into the database
        long result = db.insert(TABLE_BREAKFAST, null, values);
        if (result == -1) {
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }
    }
    //3
    // Adding a new lunch recipe into the database
    public void addLunchRecipe(String title, String hours, String minutes, String ingredients, String procedures, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_LUNCH_TITLE, title);
        values.put(COLUMN_LUNCH_HOURS, hours);
        values.put(COLUMN_LUNCH_MINUTES, minutes);
        values.put(COLUMN_LUNCH_INGREDIENTS, ingredients);
        values.put(COLUMN_LUNCH_PROCEDURES, procedures);
        values.put("category", "Lunch"); // Set the category
        values.put("user_id", userId);       // Set the user ID

        long result = db.insert(TABLE_LUNCH, null, values);
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }
    }

    //4
    // Adding a new dinner recipe into the database
    public void addDinnerRecipe(String title, String hours, String minutes, String ingredients, String procedures, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DINNER_TITLE, title);
        values.put(COLUMN_DINNER_HOURS, hours);
        values.put(COLUMN_DINNER_MINUTES, minutes);
        values.put(COLUMN_DINNER_INGREDIENTS, ingredients);
        values.put(COLUMN_DINNER_PROCEDURES, procedures);
        values.put("category", "Dinner"); // Set the category
        values.put("user_id", userId);       // Set the user ID

        long result = db.insert(TABLE_DINNER, null, values);
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }
    }

    //5
    // Adding a new snacks recipe into the database
    public void addSnacksRecipe(String title, String hours, String minutes, String ingredients, String procedures, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_SNACKS_TITLE, title);
        values.put(COLUMN_SNACKS_HOURS, hours);
        values.put(COLUMN_SNACKS_MINUTES, minutes);
        values.put(COLUMN_SNACKS_INGREDIENTS, ingredients);
        values.put(COLUMN_SNACKS_PROCEDURES, procedures);
        values.put("category", "Snacks"); // Set the category
        values.put("user_id", userId);       // Set the user ID

        long result = db.insert(TABLE_SNACKS, null, values);
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }
    }

    //6
    // Adding a new midnight snacks recipe into the database
    public void addMidnightSnacksRecipe(String title, String hours, String minutes, String ingredients, String procedures, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_MIDNIGHT_SNACKS_TITLE, title);
        values.put(COLUMN_MIDNIGHT_SNACKS_HOURS, hours);
        values.put(COLUMN_MIDNIGHT_SNACKS_MINUTES, minutes);
        values.put(COLUMN_MIDNIGHT_SNACKS_INGREDIENTS, ingredients);
        values.put(COLUMN_MIDNIGHT_SNACKS_PROCEDURES, procedures);

        values.put("category", "Snacks"); // Set the category
        values.put("user_id", userId);       // Set the user ID

        long result = db.insert(TABLE_MIDNIGHT_SNACKS, null, values);
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }
    }




    // Reading all breakfast recipes for the logged-in user
    public Cursor readAllBreakfastRecipes(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            String query = "SELECT * FROM " + TABLE_BREAKFAST + " WHERE user_id = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        }
        return cursor;
    }

    // Reading all lunch recipes for the logged-in user
    public Cursor readAllLunchRecipes(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            String query = "SELECT * FROM " + TABLE_LUNCH + " WHERE user_id = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        }
        return cursor;
    }

    // Reading all dinner recipes for the logged-in user
    public Cursor readAllDinnerRecipes(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            String query = "SELECT * FROM " + TABLE_DINNER + " WHERE user_id = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        }
        return cursor;
    }

    // Reading all snacks recipes for the logged-in user
    public Cursor readAllSnacksRecipes(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            String query = "SELECT * FROM " + TABLE_SNACKS + " WHERE user_id = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        }
        return cursor;
    }

    // Reading all midnight snacks recipes for the logged-in user
    public Cursor readAllMidnightSnacksRecipes(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            String query = "SELECT * FROM " + TABLE_MIDNIGHT_SNACKS + " WHERE user_id = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        }
        return cursor;
    }



    // Method to Update/Edit an existing BREAKFAST RECIPE
    public void updateBreakfastRecipe(String row_id, int userId, String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BREAKFAST_TITLE, title);
        values.put(COLUMN_BREAKFAST_HOURS, hours);
        values.put(COLUMN_BREAKFAST_MINUTES, minutes);
        values.put(COLUMN_BREAKFAST_INGREDIENTS, ingredients);
        values.put(COLUMN_BREAKFAST_PROCEDURES, procedures);

        long result = db.update(TABLE_BREAKFAST, values, "breakfast_id=? AND user_id=?", new String[]{row_id, String.valueOf(userId)});
        if (result == -1) {
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to Update/Edit an existing LUNCH RECIPE
    public void updateLunchRecipe(String row_id, int userId, String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LUNCH_TITLE, title);
        values.put(COLUMN_LUNCH_HOURS, hours);
        values.put(COLUMN_LUNCH_MINUTES, minutes);
        values.put(COLUMN_LUNCH_INGREDIENTS, ingredients);
        values.put(COLUMN_LUNCH_PROCEDURES, procedures);

        long result = db.update(TABLE_LUNCH, values, "lunch_id=? AND user_id=?", new String[]{row_id, String.valueOf(userId)});
        if (result == -1) {
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to Update/Edit an existing DINNER RECIPE
    public void updateDinnerRecipe(String row_id, int userId, String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DINNER_TITLE, title);
        values.put(COLUMN_DINNER_HOURS, hours);
        values.put(COLUMN_DINNER_MINUTES, minutes);
        values.put(COLUMN_DINNER_INGREDIENTS, ingredients);
        values.put(COLUMN_DINNER_PROCEDURES, procedures);

        long result = db.update(TABLE_DINNER, values, "dinner_id=? AND user_id=?", new String[]{row_id, String.valueOf(userId)});
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to Update/Edit an existing SNACKS RECIPE
    public void updateSnacksRecipe(String row_id, int userId, String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SNACKS_TITLE, title);
        values.put(COLUMN_SNACKS_HOURS, hours);
        values.put(COLUMN_SNACKS_MINUTES, minutes);
        values.put(COLUMN_SNACKS_INGREDIENTS, ingredients);
        values.put(COLUMN_SNACKS_PROCEDURES, procedures);

        long result = db.update(TABLE_SNACKS, values, "snacks_id=? AND user_id=?", new String[]{row_id, String.valueOf(userId)});
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to Update/Edit an existing MIDNIGHT SNACKS RECIPE
    public void updateMidnightSnacksRecipe(String row_id, int userId, String title, String hours, String minutes, String ingredients, String procedures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MIDNIGHT_SNACKS_TITLE, title);
        values.put(COLUMN_MIDNIGHT_SNACKS_HOURS, hours);
        values.put(COLUMN_MIDNIGHT_SNACKS_MINUTES, minutes);
        values.put(COLUMN_MIDNIGHT_SNACKS_INGREDIENTS, ingredients);
        values.put(COLUMN_MIDNIGHT_SNACKS_PROCEDURES, procedures);

        long result = db.update(TABLE_MIDNIGHT_SNACKS, values, "midnightsnacks_id=? AND user_id=?", new String[]{row_id, String.valueOf(userId)});

        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }



    //Method to delete a BREAKFAST Recipe
    public void deleteBreakfastRecipe(String row_id, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_BREAKFAST, "breakfast_id=? AND user_id=?", new String[]{row_id, String.valueOf(userId)});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    //Method to delete a LUNCH Recipe
    public void deleteLunchRecipe(String row_id, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_LUNCH, "lunch_id=? AND user_id=?", new String[]{row_id, String.valueOf(userId)});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }


    //Method to delete a DINNER Recipe
    public void deleteDinnerRecipe(String row_id, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_DINNER, "dinner_id=? AND user_id=?", new String[]{row_id, String.valueOf(userId)});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }


    //Method to delete a SNACKS Recipe
    public void deleteSnacksRecipe(String row_id, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_SNACKS, "snacks_id=? AND user_id=?", new String[]{row_id, String.valueOf(userId)});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }


    //Method to delete a MIDNIGHT SNACKS Recipe
    public void deleteMidnightSnacksRecipe(String row_id, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_MIDNIGHT_SNACKS, "midnightsnacks_id=? AND user_id=?", new String[]{row_id, String.valueOf(userId)});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }


//METHOD TO fetch recipes from all category tables and store them in a list
public List<Recipe> getAllRecipes(int userId) {
    List<Recipe> recipes = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();

    // Query for each category, including all fields
    Cursor breakfastCursor = db.rawQuery("SELECT title, hours, minutes, ingredients, procedures FROM Breakfast WHERE user_id = ?", new String[]{String.valueOf(userId)});
    addRecipesToList(recipes, breakfastCursor, "Breakfast");

    Cursor lunchCursor = db.rawQuery("SELECT title, hours, minutes, ingredients, procedures FROM Lunch WHERE user_id = ?", new String[]{String.valueOf(userId)});
    addRecipesToList(recipes, lunchCursor, "Lunch");

    Cursor dinnerCursor = db.rawQuery("SELECT title, hours, minutes, ingredients, procedures FROM Dinner WHERE user_id = ?", new String[]{String.valueOf(userId)});
    addRecipesToList(recipes, dinnerCursor, "Dinner");

    Cursor snacksCursor = db.rawQuery("SELECT title, hours, minutes, ingredients, procedures FROM Snacks WHERE user_id = ?", new String[]{String.valueOf(userId)});
    addRecipesToList(recipes, snacksCursor, "Snacks");

    Cursor midnightSnacksCursor = db.rawQuery("SELECT title, hours, minutes, ingredients, procedures FROM MidnightSnacks WHERE user_id = ?", new String[]{String.valueOf(userId)});
    addRecipesToList(recipes, midnightSnacksCursor, "Midnight Snacks");

    return recipes;
}

    private void addRecipesToList(List<Recipe> recipes, Cursor cursor, String category) {
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                String hours = cursor.getString(1);
                String minutes = cursor.getString(2);
                String ingredients = cursor.getString(3); // New
                String procedures = cursor.getString(4);  // New
                recipes.add(new Recipe(title, hours, minutes, category, ingredients, procedures));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


    // Method to get user details based on user_id
    public Cursor getUserDetailsById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE user_id = ?",
                new String[]{String.valueOf(userId)}
        );
        return cursor;
    }




    public Cursor getAllBreakfastRecipesForCalendar() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT B.breakfast_id, B.title, B.hours, B.minutes, U.user_id " +
                "FROM " + TABLE_BREAKFAST + " AS B " +
                "JOIN " + TABLE_USERS + " AS U ON B.user_id = U.user_id";
        return db.rawQuery(query, null);
    }



    public Cursor getAllLunchRecipesForCalendar() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT B.lunch_id, B.title, B.hours, B.minutes, U.user_id " +
                "FROM " + TABLE_LUNCH + " AS B " +
                "JOIN " + TABLE_USERS + " AS U ON B.user_id = U.user_id";
        return db.rawQuery(query, null);
    }



    public Cursor getAllSnacksRecipesForCalendar() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT B.snacks_id, B.title, B.hours, B.minutes, U.user_id " +
                "FROM " + TABLE_SNACKS + " AS B " +
                "JOIN " + TABLE_USERS + " AS U ON B.user_id = U.user_id";
        return db.rawQuery(query, null);
    }



    public Cursor getAllDinnerRecipesForCalendar() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT B.dinner_id, B.title, B.hours, B.minutes, U.user_id " +
                "FROM " + TABLE_DINNER + " AS B " +
                "JOIN " + TABLE_USERS + " AS U ON B.user_id = U.user_id";
        return db.rawQuery(query, null);
    }



    public Cursor getAllMidnightSnacksRecipesForCalendar() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT B.midnightsnacks_id, B.title, B.hours, B.minutes, U.user_id " +
                "FROM " + TABLE_MIDNIGHT_SNACKS + " AS B " +
                "JOIN " + TABLE_USERS + " AS U ON B.user_id = U.user_id";
        return db.rawQuery(query, null);
    }


    public boolean isDateInCalendar(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_CALENDAR_SELECTED_DATE + " = ?";
        String[] selectionArgs = { date };
        Cursor cursor = db.query(TABLE_CALENDAR, null, selection, selectionArgs, null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        return exists;
    }


    public void updateCalendarEntry(String date, int recipeId, String category, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String column = getColumnForCategory(category);
        values.put(column, recipeId);
        values.put("user_id", userId);
        db.update(TABLE_CALENDAR, values, COLUMN_CALENDAR_SELECTED_DATE + " = ?", new String[]{date});
    }


    public void insertCalendarEntry(String date, int recipeId, String category, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CALENDAR_SELECTED_DATE, date);
        values.put("user_id", userId); // Insert the user_id
        String column = getColumnForCategory(category);
        values.put(column, recipeId);

        long result = db.insert(TABLE_CALENDAR, null, values);
        if (result == -1) {
            Log.e("DatabaseHelper", "Failed to insert calendar entry for date: " + date + " and category: " + category);
        } else {
            Log.d("DatabaseHelper", "Calendar entry inserted for date: " + date + " and category: " + category);
        }
    }



    private String getColumnForCategory(String category) {
        switch (category) {
            case "Breakfast":
                return COLUMN_BREAKFAST_ID;
            case "Lunch":
                return COLUMN_LUNCH_ID;
            case "Snacks":
                return COLUMN_SNACKS_ID;
            case "Dinner":
                return COLUMN_DINNER_ID;
            case "Midnight Snacks":
                return COLUMN_MIDNIGHT_SNACKS_ID;
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }


    public Cursor getCalendarEntryByDate(String date, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_CALENDAR_SELECTED_DATE + " = ? AND user_id = ?";
        String[] selectionArgs = { date, String.valueOf(userId) };
        return db.query(TABLE_CALENDAR, null, selection, selectionArgs, null, null, null);
    }



    public RecipeCalendar getRecipeById(int recipeId, String category, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        RecipeCalendar recipe = null;


        switch (category) {
            case "Breakfast":
                cursor = db.query(TABLE_BREAKFAST, null, COLUMN_BREAKFAST_ID + " = ?", new String[]{String.valueOf(recipeId)}, null, null, null);
                break;
            case "Lunch":
                cursor = db.query(TABLE_LUNCH, null, COLUMN_LUNCH_ID + " = ?", new String[]{String.valueOf(recipeId)}, null, null, null);
                break;
            case "Snacks":
                cursor = db.query(TABLE_SNACKS, null, COLUMN_SNACKS_ID + " = ?", new String[]{String.valueOf(recipeId)}, null, null, null);
                break;
            case "Dinner":
                cursor = db.query(TABLE_DINNER, null, COLUMN_DINNER_ID + " = ?", new String[]{String.valueOf(recipeId)}, null, null, null);
                break;
            case "Midnight Snacks":
                cursor = db.query(TABLE_MIDNIGHT_SNACKS, null, COLUMN_MIDNIGHT_SNACKS_ID + " = ?", new String[]{String.valueOf(recipeId)}, null, null, null);
                break;
        }

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    String title = cursor.getString(cursor.getColumnIndex(COLUMN_MIDNIGHT_SNACKS_TITLE)); // Adjust based on the table
                    int hours = cursor.getInt(cursor.getColumnIndex(COLUMN_MIDNIGHT_SNACKS_HOURS)); // Adjust based on the table
                    int minutes = cursor.getInt(cursor.getColumnIndex(COLUMN_MIDNIGHT_SNACKS_MINUTES)); // Adjust based on the table
                    recipe = new RecipeCalendar(recipeId, title, hours, minutes, category, userId);
                }
            } finally {
                cursor.close();
            }
        }
        return recipe;


    }


}
