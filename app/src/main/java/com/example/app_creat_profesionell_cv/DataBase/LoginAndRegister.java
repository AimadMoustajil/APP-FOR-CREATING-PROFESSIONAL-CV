package com.example.app_creat_profesionell_cv.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginAndRegister extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "loginAndRegister.db";
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_NUMBER_PHONE = "numberPhone";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_IMAGE = "urlImage";

    public static final String SQL_CREATE_ENTRIES = String.format(
            "CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT)",
            TABLE_NAME, COLUMN_USER_NAME, COLUMN_NUMBER_PHONE, COLUMN_PASSWORD, COLUMN_IMAGE
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

    public void add(String name, String numberPhone, String password, String srcImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_NAME, name);
            values.put(COLUMN_NUMBER_PHONE, numberPhone);
            values.put(COLUMN_PASSWORD, hashPassword(password));
            values.put(COLUMN_IMAGE, srcImage);

            db.insert(TABLE_NAME, null, values);
        } catch (SQLException e) {
            Log.e("DB_ERROR", "Error while inserting into the database", e);
        } finally {
            db.close();
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e("HASH_ERROR", "No such hashing algorithm", e);
            return password;
        }
    }

    public void getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_USER_NAME, COLUMN_NUMBER_PHONE, COLUMN_PASSWORD, COLUMN_IMAGE
        };
        Cursor cursor = db.query(
                TABLE_NAME, projection, null, null, null, null, null
        );

        while (cursor.moveToNext()) {
            String nameUser = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
            String numberPhone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER_PHONE));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            String srcImage = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));

            Log.e("AG", String.format("%s - %s - %s - %s", nameUser, numberPhone, password, srcImage));
        }
        cursor.close();
    }

    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_USER_NAME, COLUMN_PASSWORD
        };
        String selection = COLUMN_USER_NAME + " = ?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query(
                TABLE_NAME, projection, selection, selectionArgs, null, null, null
        );
        boolean isUserExists = false;
        if (cursor != null && cursor.moveToFirst()) {
            String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            if (hashPassword(password).equals(storedPassword)) {
                isUserExists = true;
            }
            cursor.close();
        }
        db.close();
        return isUserExists;
    }

}
