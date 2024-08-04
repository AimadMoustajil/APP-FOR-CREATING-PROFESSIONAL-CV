package com.example.app_creat_profesionell_cv.DB;

import android.annotation.SuppressLint;
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
    private static final String DATABASE_NAME = "info_projet";
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
        db.execSQL(CREATE_INFO_TABLE);
        Log.d("Projet", "Database table created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
        onCreate(db);
        Log.d("Projet", "Database table upgraded.");
    }

    public boolean addInfoProjet(InfoProjet infoProjet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValues(infoProjet);
        long result = db.insertWithOnConflict(TABLE_INFO, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        Log.d("Projet", "Insert result: " + result);
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
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME_PROJET);
            int sujetIndex = cursor.getColumnIndex(COLUMN_SUJET_PROJET);
            int dateStartIndex = cursor.getColumnIndex(COLUMN_DATE_START);
            int dateEndIndex = cursor.getColumnIndex(COLUMN_DATE_END);
            int resumeIndex = cursor.getColumnIndex(COLUMN_RESUME);

            while (cursor.moveToNext()) {
                InfoProjet infoProjet = new InfoProjet();
                infoProjet.setNomEntreprise(cursor.isNull(nameIndex) ? null : cursor.getString(nameIndex));
                infoProjet.setTitreProjet(cursor.isNull(sujetIndex) ? null : cursor.getString(sujetIndex));
                infoProjet.setDateDebut(cursor.isNull(dateStartIndex) ? null : cursor.getString(dateStartIndex));
                infoProjet.setDateFin(cursor.isNull(dateEndIndex) ? null : cursor.getString(dateEndIndex));
                infoProjet.setResume(cursor.isNull(resumeIndex) ? null : cursor.getString(resumeIndex));
                infoProjets.add(infoProjet);
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
        db.execSQL("DELETE FROM " + TABLE_INFO);
        db.close();
        Log.d("Projet", "All data cleared from table " + TABLE_INFO);
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
