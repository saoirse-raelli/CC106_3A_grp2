package com.example.prepmate;

import androidx.annotation.Nullable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database and table information
    private final Context context;
    private static final String DATABASE_NAME = "PrepMate.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_NOTES = "Notes";
    //private static final String TABLE_BREAKFAST = "Breakfast";
    //private static final String TABLE_LUNCH = "Lunch";
    //private static final String TABLE_DINNER = "Dinner";
    //private static final String TABLE_SNACKS = "Snacks";
    //private static final String TABLE_MIDNIGHT_SNACKS = "MidnightSnacks";

    // Column names for Users table
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_LASTNAME = "lastname";
    public static final String COLUMN_USER_FIRSTNAME = "firstname";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";

    // Column names for Notes table
    // Column names for all tables (will be the same for each category)
    //bale rename nalang natin na instead column_note_ edi column_recipe..
    //di na daw kelangan magdagdag ng column to identify the type kase may kanya kanya nang table sa database
    //pero same same lang to gagamitin
    public static final String COLUMN_NOTE_ID = "note_id";
    public static final String COLUMN_NOTE_TITLE = "title";
    public static final String COLUMN_NOTE_HOURS = "hours";
    public static final String COLUMN_NOTE_MINUTES = "minutes";
    public static final String COLUMN_NOTE_INGREDIENTS = "ingredients";
    public static final String COLUMN_NOTE_PROCEDURES = "procedures";


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
                + COLUMN_NOTE_PROCEDURES + " TEXT);";
        db.execSQL(createNotesTable);

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
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop the old table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);   //THIS ONE IS TATANGGALIN NA PAG OKAY NA YUNG IBAAA

        //eto na yong para sa bawat type...  i-uncomment nalang
        //db.execSQL("DROP TABLE IF EXISTS" + TABLE_BREAKFAST;
        //db.execSQL("DROP TABLE IF EXISTS" + TABLE_LUNCH;
        //db.execSQL("DROP TABLE IF EXISTS" + TABLE_DINNER;
        //db.execSQL("DROP TABLE IF EXISTS" + TABLE_SNACKS;
        //db.execSQL("DROP TABLE IF EXISTS" + TABLE_MIDNIGHT_SNACK;

        onCreate(db);
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
        values.put(COLUMN_NOTE_PROCEDURES, procedures);

        long result = db.insert(TABLE_NOTES, null, values);
        if(result == -1){
            Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }
    }
    //Dito nalang pag sunud sunudin method to add new recipe into the database for each type
    //public void addBreakfastRecipe(String title, String hours, String minutes, String ingredients, String procedures) { ... }
    //public void addLunchRecipe(String title, String hours, String minutes, String ingredients, String procedures) { ... }
    //public void addDinnerRecipe(String title, String hours, String minutes, String ingredients, String procedures) { ... }
    //public void addSnacksRecipe(String title, String hours, String minutes, String ingredients, String procedures) { ... }
    //public void addMidnightSnacksRecipe(String title, String hours, String minutes, String ingredients, String procedures) { ... }




    //3
    // Reading all recipes from the each table
    public Cursor readAllData(){
        String createTable = "SELECT * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(createTable, null);

        }
        return cursor;
    }
    // Reading all recipes from the each table
    //public Cursor readAllBreakfastRecipes() { ... }
    //public Cursor readAllLunchRecipes() { ... }
    //public Cursor readAllDinnerRecipes() { ... }
    //public Cursor readAllSnacksRecipes() { ... }
    //public Cursor readAllMidnightSnacksRecipes() { ... }




    //4
    // Method to Update/Edit an existing
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
    //Repeat lang din kada type here nalang sa baba to Update/Edit an existing for each type
    //public void updateBreakfastRecipe(String row_id, String title, String hours, String minutes, String ingredients, String procedures) { ... }
    //public void updateLunchRecipe(String row_id, String title, String hours, String minutes, String ingredients, String procedures) { ... }
    //public void updateDinnerRecipe(String row_id, String title, String hours, String minutes, String ingredients, String procedures) { ... }
    //public void updateSnacksRecipe(String row_id, String title, String hours, String minutes, String ingredients, String procedures) { ... }
    //public void updateMidnightSnacksRecipe(String row_id, String title, String hours, String minutes, String ingredients, String procedures) { ... }



    //5
    //Method to delete a Recipe
    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NOTES, "note_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
    //Method to delete a Recipe for each type
    //public void deleteBreakfastRecipe(String row_id) { ... }
    //public void deleteLunchRecipe(String row_id) { ... }
    //public void deleteDinnerRecipe(String row_id) { ... }
    //public void deleteSnacksRecipe(String row_id) { ... }
    //public void deleteMidnightSnacksRecipe(String row_id) { ... }

}
