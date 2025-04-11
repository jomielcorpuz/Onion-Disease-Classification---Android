package com.example.onionapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataManager {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DataManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Open the database for reading or writing
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    // Close the database
    public void close() {
        dbHelper.close();
    }

    // Insert a new record into the database
    public long insertRecord(String name, String value, String chemicalRecommendation, String carefultips, String referenc) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, name);
        values.put(DBHelper.COLUMN_VALUE, value);
        values.put(DBHelper.COLUMN_CHEMICAL_RECOMMENDATION, chemicalRecommendation);
        values.put(DBHelper.COLUMN_CAREFULTIPS, carefultips);
        values.put(DBHelper.COLUMN_REFERENCES, referenc);
        return database.insert(DBHelper.TABLE_NAME, null, values);
    }


    // Retrieve all records from the database
    public Cursor getAllRecords() {
        Cursor cursor = database.query(
                DBHelper.TABLE_NAME,
                new String[]{DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME, DBHelper.COLUMN_VALUE, DBHelper.COLUMN_CHEMICAL_RECOMMENDATION, DBHelper.COLUMN_CAREFULTIPS, DBHelper.COLUMN_REFERENCES},
                null,
                null,
                null,
                null,
                null
        );

        return cursor;
    }

    // Update a record in the database
    public int updateRecord(long recId, String name, String value, String chemValue, String carefultipsVal, String refValue) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, name);
        values.put(DBHelper.COLUMN_VALUE, value);
        values.put(DBHelper.COLUMN_CHEMICAL_RECOMMENDATION, chemValue);
        values.put(DBHelper.COLUMN_CAREFULTIPS, carefultipsVal);
        values.put(DBHelper.COLUMN_REFERENCES, refValue);
        return database.update(DBHelper.TABLE_NAME, values, DBHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(recId)});
    }

    public boolean isRecordExist(String name) {
        Cursor cursor = null;

        try {
            // Query the database to check if the record with the given name exists
            cursor = database.query(
                    DBHelper.TABLE_NAME,
                    new String[]{DBHelper.COLUMN_NAME},
                    DBHelper.COLUMN_NAME + " = ?",
                    new String[]{name},
                    null,
                    null,
                    null
            );

            // If the cursor has any rows, then the record already exists
            return cursor != null && cursor.moveToFirst();
        } finally {
            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
