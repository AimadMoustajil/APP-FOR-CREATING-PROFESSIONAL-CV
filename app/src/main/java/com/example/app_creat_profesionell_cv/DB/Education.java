package com.example.app_creat_profesionell_cv.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.app_creat_profesionell_cv.Classes.InfoEducation;

import java.util.ArrayList;

public class Education extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "info_education";

    // Table name
    private static final String TABLE_INFO = "info";

    // Column names
    private static final String COLUMN_NAME_SCHOOL = "n_school";
    private static final String COLUMN_NAME_METIER = "n_metier";
    private static final String COLUMN_DATE_START = "from_date";
    private static final String COLUMN_DATE_END = "to_date";

    // Create table SQL query
    private static final String CREATE_INFO_TABLE = "CREATE TABLE " + TABLE_INFO + "("
            + COLUMN_NAME_SCHOOL + " TEXT,"
            + COLUMN_NAME_METIER + " TEXT,"
            + COLUMN_DATE_START + " TEXT,"
            + COLUMN_DATE_END + " TEXT"
            + ")";

    public Education(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
        onCreate(db);
    }

    public boolean addInfoEducation(InfoEducation infoEducation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValues(infoEducation);

        // Insert or replace Facture
        long result = db.insertWithOnConflict(TABLE_INFO, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        // Close the database
        db.close();

        // Check if insertion was successful
        return result != -1;
    }

    private ContentValues populateContentValues(InfoEducation infoEducation) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_SCHOOL, infoEducation.getShool());
        values.put(COLUMN_NAME_METIER, infoEducation.getMetier());
        values.put(COLUMN_DATE_START, infoEducation.getStartYier());
        values.put(COLUMN_DATE_END, infoEducation.getEndYier());
        return values;
    }

    public ArrayList<InfoEducation> getAllInfoEducation() {
        ArrayList<InfoEducation> infoEducations = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_INFO;

        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.rawQuery(selectQuery, null)) {
            while (cursor.moveToNext()) {
                infoEducations.add(createInfoEducationFromCursor(cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return infoEducations;
    }

    @SuppressLint("Range")
    private InfoEducation createInfoEducationFromCursor(Cursor cursor) {
        InfoEducation infoEducation = new InfoEducation();
        infoEducation.setShool(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_SCHOOL)));
        infoEducation.setMetier(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_METIER)));
        infoEducation.setStartYier(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_START)));
        infoEducation.setEndYier(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_END)));
        return infoEducation;
    }

    public void clearAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_INFO);
        db.close();
    }

    public boolean isDatabaseEmpty() {
        boolean isEmpty = true;
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.rawQuery(countQuery, null)) {
            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                isEmpty = count == 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return isEmpty;
    }
}
