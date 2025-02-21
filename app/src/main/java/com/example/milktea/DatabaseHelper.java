package com.example.milktea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "genztea.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {

        MyDatabase.execSQL("CREATE TABLE users (user_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT UNIQUE, password TEXT)");

        // Updated milktea table to include image_uri column
        MyDatabase.execSQL("CREATE TABLE milktea (milktea_id INTEGER PRIMARY KEY AUTOINCREMENT, milktea_name TEXT, description TEXT, milktea_price REAL, image_uri TEXT)");

        MyDatabase.execSQL("CREATE TABLE addonz (addonz_id INTEGER PRIMARY KEY AUTOINCREMENT, addonz_name TEXT, addonz_price REAL)");

        MyDatabase.execSQL("CREATE TABLE snacks (snack_id INTEGER PRIMARY KEY AUTOINCREMENT, snacks_name TEXT, snacks_price REAL)");

        MyDatabase.execSQL("CREATE TABLE receipt (receipt_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER NOT NULL, username TEXT NOT NULL, milktea_name TEXT, addonz_name TEXT, snacks_name TEXT, total_price REAL, FOREIGN KEY(user_id) REFERENCES users(user_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int il) {
        // Drop tables if they exist
        MyDatabase.execSQL("DROP TABLE IF EXISTS users");
        MyDatabase.execSQL("DROP TABLE IF EXISTS milktea");
        MyDatabase.execSQL("DROP TABLE IF EXISTS addonz");
        MyDatabase.execSQL("DROP TABLE IF EXISTS snacks");
        MyDatabase.execSQL("DROP TABLE IF EXISTS receipt");
        onCreate(MyDatabase);
    }

    // Insert data for users
    public Boolean insertUsersData(String username, String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);

        return result != -1;
    }

    // Insert a new milk tea item
    public Boolean insertMilkteaItem(String milkteaName, String description, String price, String imageUri) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("milktea_name", milkteaName);
        contentValues.put("description", description);
        contentValues.put("milktea_price", price);
        contentValues.put("image_uri", imageUri); // Save the image URI

        long result = MyDatabase.insert("milktea", null, contentValues);

        return result != -1;
    }

    // Check if a username already exists
    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        return false;
    }

    // Check if an email already exists
    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});

        return cursor.getCount() > 0;
    }

    // Check if email and password are correct
    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});

        return cursor.getCount() > 0;
    }
}
