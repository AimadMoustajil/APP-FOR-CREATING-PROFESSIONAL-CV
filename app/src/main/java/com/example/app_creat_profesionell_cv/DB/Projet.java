package com.example.app_creat_profesionell_cv.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.app_creat_profesionell_cv.Classes.InfoProjet;

import java.util.ArrayList;

public class Projet extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "info_of_projet";
    private static final String TABLE_INFO = "myProjet";
    private static final String COLUMN_NAME_PROJET = "n_projet";
    private static final String COLUMN_SUJET_PROJET = "sujet_projet";
    private static final String COLUMN_DATE_START = "from_date";
    private static final String COLUMN_DATE_END = "to_date";
    private static final String COLUMN_RESUME = "resume";

    private static final String CREATE_INFO_TABLE = "CREATE TABLE " + TABLE_INFO + " ("
            + COLUMN_NAME_PROJET + " TEXT, "
            + COLUMN_SUJET_PROJET + " TEXT, "
            + COLUMN_DATE_START + " TEXT, "
            + COLUMN_DATE_END + " TEXT, "
            + COLUMN_RESUME + " TEXT);";

    public Projet(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_INFO_TABLE);
            Log.d("Projet", "Database table created.");
        } catch (Exception e) {
            Log.e("Projet", "Error creating database table", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
            onCreate(db);
            Log.d("Projet", "Database table upgraded.");
        } catch (Exception e) {
            Log.e("Projet", "Error upgrading database table", e);
        }
    }

    public boolean addInfoProjet(InfoProjet infoProjet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValues(infoProjet);
        long result = -1;
        try {
            result = db.insertWithOnConflict(TABLE_INFO, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            Log.d("Projet", "Insert result: " + result);
        } catch (Exception e) {
            Log.e("Projet", "Error inserting infoProjet", e);
        } finally {
            db.close();
        }
        return result != -1;
    }

    private ContentValues populateContentValues(InfoProjet infoProjet) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_PROJET, infoProjet.getNomEntreprise());
        values.put(COLUMN_SUJET_PROJET, infoProjet.getTitreProjet());
        values.put(COLUMN_DATE_START, infoProjet.getDateDebut());
        values.put(COLUMN_DATE_END, infoProjet.getDateFin());
        values.put(COLUMN_RESUME, infoProjet.getResume());
        return values;
    }

    public ArrayList<InfoProjet> getAllInfoProjets() {
        ArrayList<InfoProjet> infoProjets = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_INFO;
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.rawQuery(selectQuery, null)) {
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME_PROJET);
                int sujetIndex = cursor.getColumnIndex(COLUMN_SUJET_PROJET);
                int dateStartIndex = cursor.getColumnIndex(COLUMN_DATE_START);
                int dateEndIndex = cursor.getColumnIndex(COLUMN_DATE_END);
                int resumeIndex = cursor.getColumnIndex(COLUMN_RESUME);

                do {
                    InfoProjet infoProjet = new InfoProjet();
                    infoProjet.setNomEntreprise(cursor.getString(nameIndex));
                    infoProjet.setTitreProjet(cursor.getString(sujetIndex));
                    infoProjet.setDateDebut(cursor.getString(dateStartIndex));
                    infoProjet.setDateFin(cursor.getString(dateEndIndex));
                    infoProjet.setResume(cursor.getString(resumeIndex));
                    infoProjets.add(infoProjet);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Projet", "Error retrieving info projets", e);
        } finally {
            db.close();
        }
        Log.d("Projet", "Retrieved " + infoProjets.size() + " info projets.");
        return infoProjets;
    }

    public void clearAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_INFO);
            Log.d("Projet", "All data cleared from table " + TABLE_INFO);
        } catch (Exception e) {
            Log.e("Projet", "Error clearing all data", e);
        } finally {
            db.close();
        }
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
            Log.e("Projet", "Error checking if database is empty", e);
        } finally {
            db.close();
        }
        Log.d("Projet", "Database empty check: " + (isEmpty ? "Empty" : "Not Empty"));
        return isEmpty;
    }
}
