package com.example.app_creat_profesionell_cv.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.app_creat_profesionell_cv.Classes.InfoExperience;

import java.util.ArrayList;

public class ExperinceDeTravaile extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "info_experience";

    // Table name
    private static final String TABLE_INFO = "info";

    // Column names
    private static final String COLUMN_NAME_COMPANY = "n_company";
    private static final String COLUMN_NAME_METIER = "n_metier";
    private static final String COLUMN_DATE_START = "from_date";
    private static final String COLUMN_DATE_END = "to_date";
    private static final String COLUMN_RESUME = "resume";

    private static final String CREATE_INFO_TABLE = "CREATE TABLE " + TABLE_INFO + "("
            + COLUMN_NAME_COMPANY + " TEXT,"
            + COLUMN_NAME_METIER + " TEXT,"
            + COLUMN_DATE_START + " TEXT,"
            + COLUMN_DATE_END + " TEXT,"
            + COLUMN_RESUME + " TEXT"
            + ")";

    public ExperinceDeTravaile(@Nullable Context context) {
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

    public boolean addInfoExperience(InfoExperience infoExperience) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValues(infoExperience);

        // Insert or replace Facture
        long result = db.insertWithOnConflict(TABLE_INFO, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        // Close the database
        db.close();

        // Check if insertion was successful
        return result != -1;
    }

    private ContentValues populateContentValues(InfoExperience infoExperience) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_COMPANY, infoExperience.getNomEntreprise());
        values.put(COLUMN_NAME_METIER, infoExperience.getTitreDePoste());
        values.put(COLUMN_DATE_START, infoExperience.getDateDébut());
        values.put(COLUMN_DATE_END, infoExperience.getDateDeFin());
        values.put(COLUMN_RESUME, infoExperience.getRésumé());
        return values;
    }

    public ArrayList<InfoExperience> getAllInfoExperience() {
        ArrayList<InfoExperience> infoExperiences = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_INFO;

        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.rawQuery(selectQuery, null)) {
            while (cursor.moveToNext()) {
                infoExperiences.add(createInfoExperienceFromCursor(cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return infoExperiences;
    }

    @SuppressLint("Range")
    private InfoExperience createInfoExperienceFromCursor(Cursor cursor) {
        InfoExperience infoExperience = new InfoExperience();
        infoExperience.setNomEntreprise(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_COMPANY)));
        infoExperience.setTitreDePoste(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_METIER)));
        infoExperience.setDateDébut(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_START)));
        infoExperience.setDateDeFin(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_END)));
        infoExperience.setRésumé(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESUME)));
        return infoExperience;
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
