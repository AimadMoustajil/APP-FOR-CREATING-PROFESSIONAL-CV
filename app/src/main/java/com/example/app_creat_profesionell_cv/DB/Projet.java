package com.example.app_creat_profesionell_cv.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.app_creat_profesionell_cv.Classes.InfoProjet;

import java.util.ArrayList;

public class Projet extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "info_projet";

    // Table name
    private static final String TABLE_INFO = "info";

    // Column names
    private static final String COLUMN_NAME_PROJET = "n_projet";
    private static final String COLUMN_SUJET_PROJET = "sujet_projet";
    private static final String COLUMN_DATE_START = "from_date";
    private static final String COLUMN_DATE_END = "to_date";
    private static final String COLUMN_RESUME = "resume";

    private static final String CREATE_INFO_TABLE = "CREATE TABLE " + TABLE_INFO + "("
            + COLUMN_NAME_PROJET + " TEXT,"
            + COLUMN_SUJET_PROJET + " TEXT,"
            + COLUMN_DATE_START + " TEXT,"
            + COLUMN_DATE_END + " TEXT,"
            + COLUMN_RESUME + " TEXT"
            + ")";

    public Projet(@Nullable Context context) {
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

    public boolean addInfoProjet(InfoProjet infoProjet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValues(infoProjet);

        // Insert or replace project info
        long result = db.insertWithOnConflict(TABLE_INFO, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        // Close the database
        db.close();

        // Check if insertion was successful
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
            while (cursor.moveToNext()) {
                infoProjets.add(createInfoProjetFromCursor(cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return infoProjets;
    }

    @SuppressLint("Range")
    private InfoProjet createInfoProjetFromCursor(Cursor cursor) {
        InfoProjet infoProjet = new InfoProjet();
        infoProjet.setNomEntreprise(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_PROJET)));
        infoProjet.setTitreProjet(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUJET_PROJET)));
        infoProjet.setDateDebut(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_START)));
        infoProjet.setDateFin(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_END)));
        infoProjet.setResume(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESUME)));
        return infoProjet;
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
