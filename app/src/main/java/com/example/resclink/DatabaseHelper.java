package com.example.resclink;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "AccidentAlertDB";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "phone TEXT NOT NULL," +
                "password_hash TEXT)");

        db.execSQL("CREATE TABLE EmergencyContacts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "phone TEXT NOT NULL)");

        db.execSQL("CREATE TABLE AccidentReports (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "location TEXT," +
                "date TEXT," +
                "time TEXT," +
                "description TEXT)");

        db.execSQL("CREATE TABLE SafetyTips (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tip TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
    }

    public boolean addContact(String name, String phone) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);

        return db.insert("EmergencyContacts", null, values) != -1;
    }

    public Cursor getContacts() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query("EmergencyContacts", null,
                null, null, null, null, "name ASC");
    }

    public boolean deleteContact(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("EmergencyContacts", "id=?",
                new String[]{String.valueOf(id)}) > 0;
    }

    public boolean addAccident(String location,
                               String date,
                               String time,
                               String description) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("location", location);
        values.put("date", date);
        values.put("time", time);
        values.put("description", description);

        return db.insert("AccidentReports", null, values) != -1;
    }

    public Cursor getAccidents() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query("AccidentReports",
                null, null, null, null, null, "id DESC");
    }

    // GET A SINGLE ACCIDENT REPORT
    public Cursor getAccidentById(int id) {
        SQLiteDatabase db = getReadableDatabase();

        return db.query("AccidentReports",
                null, "id=?",
                new String[]{String.valueOf(id)},
                null, null, null);
    }

    public int getAccidentCount() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM AccidentReports", null);

        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }

    public boolean addSafetyTip(String tip) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tip", tip);

        return db.insert("SafetyTips", null, values) != -1;
    }

    public Cursor getSafetyTips() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query("SafetyTips",
                null, null, null, null, null, "id ASC");
    }
}