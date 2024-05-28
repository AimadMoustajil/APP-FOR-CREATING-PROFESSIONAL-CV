package com.example.app_creat_profesionell_cv.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class LoginAndRegister extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "loginAndRegister.db";
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_NUMBER_PHONE = "numberPhone";
    public static final String COLUMN_PASSWORD = "password";

    public static final String SQL_CREATE_ENTRIES = String.format(
            "CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT)",
            TABLE_NAME, COLUMN_USER_NAME, COLUMN_NUMBER_PHONE, COLUMN_PASSWORD
    );

    public static final String SQL_DELETE_ENTRIES = String.format(
            "DROP TABLE IF EXISTS %s", TABLE_NAME
    );

    public LoginAndRegister(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void add(String name, String numberPhone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_NUMBER_PHONE, numberPhone);
        values.put(COLUMN_PASSWORD, password);

        db.insert(TABLE_NAME, null, values);
    }

    public void getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_USER_NAME, COLUMN_NUMBER_PHONE, COLUMN_PASSWORD
        };
        Cursor cursor = db.query(
                TABLE_NAME, projection, null, null, null, null, null
        );

        while (cursor.moveToNext()) {
            String nameUser = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
            String numberPhone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER_PHONE));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));

            Log.e("AG", String.format("%s - %s - %s", nameUser, numberPhone, password));
        }
        cursor.close();
    }
}
