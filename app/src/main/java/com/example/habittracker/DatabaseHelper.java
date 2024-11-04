package com.example.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HabitTracker.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla de usuarios
    public static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USERNAME = "username";

    // Tabla de hábitos
    private static final String TABLE_HABITS = "habits";
    private static final String COLUMN_HABIT_ID = "habit_id";
    private static final String COLUMN_HABIT_NAME = "habit_name";
    private static final String COLUMN_HABIT_DESCRIPTION = "habit_description";
    private static final String COLUMN_HABIT_PRIORITY = "habit_priority";
    private static final String COLUMN_HABIT_COMPLETED = "habit_completed";
    private static final String COLUMN_USER_ID_FK = "user_id_fk";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT NOT NULL)";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_HABITS_TABLE = "CREATE TABLE " + TABLE_HABITS + "("
                + COLUMN_HABIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                + COLUMN_HABIT_DESCRIPTION + " TEXT, "
                + COLUMN_HABIT_PRIORITY + " INTEGER, "
                + COLUMN_HABIT_COMPLETED + " INTEGER DEFAULT 0, "
                + COLUMN_USER_ID_FK + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";
        db.execSQL(CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITS);
        onCreate(db);
    }

    public void addHabit(String name, String description, int priority, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HABIT_NAME, name);
        values.put(COLUMN_HABIT_DESCRIPTION, description);
        values.put(COLUMN_HABIT_PRIORITY, priority);
        values.put(COLUMN_HABIT_COMPLETED, 0);
        values.put(COLUMN_USER_ID_FK, getUserId(username));

        db.insert(TABLE_HABITS, null, values);
        db.close();
    }

    public int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USER_ID + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            cursor.close();
            return userId;
        }
        cursor.close();
        return -1;
    }

    public void updateHabitCompletion(int habitId, boolean isCompleted) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HABIT_COMPLETED, isCompleted ? 1 : 0);

        db.update(TABLE_HABITS, values, COLUMN_HABIT_ID + " = ?", new String[]{String.valueOf(habitId)});
        db.close();
    }

    public List<Habit> getHabitsForUser(String username) {
        List<Habit> habits = new ArrayList<>();
        int userId = getUserId(username);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HABITS + " WHERE " + COLUMN_USER_ID_FK + " = ?", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_HABIT_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_HABIT_NAME));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_HABIT_DESCRIPTION));
                int priority = cursor.getInt(cursor.getColumnIndex(COLUMN_HABIT_PRIORITY));
                boolean isCompleted = cursor.getInt(cursor.getColumnIndex(COLUMN_HABIT_COMPLETED)) == 1;

                habits.add(new Habit(id, name, description, priority, isCompleted));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return habits;
    }

    public boolean checkUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{username});

        boolean userExists = cursor.moveToFirst();
        cursor.close();
        return userExists;
    }

    public void addUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);

        db.insert(TABLE_USERS, null, values);
        db.close();
    }



}

