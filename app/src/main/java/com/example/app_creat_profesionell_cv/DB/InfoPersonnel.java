package com.example.app_creat_profesionell_cv.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.app_creat_profesionell_cv.Classes.InfoPersonnelle;

import java.util.ArrayList;

public class InfoPersonnel extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "info_personnelle";
    private static final String TABLE_FACTURES = "info";

    private static final String COLUMN_IMAGE_OF_USERS = "images";
    private static final String COLUMN_FIRST_NAME = "f_name";
    private static final String COLUMN_LAST_NAME = "l_name";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PAYS = "pays";
    private static final String COLUMN_VILLE = "ville";
    private static final String COLUMN_JOBS = "job";

    public InfoPersonnel(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table SQL query
    private static final String CREATE_FACTURES_TABLE = "CREATE TABLE " + TABLE_FACTURES + "("
            + COLUMN_IMAGE_OF_USERS + " TEXT,"
            + COLUMN_FIRST_NAME + " TEXT,"
            + COLUMN_LAST_NAME + " TEXT,"
            + COLUMN_PHONE_NUMBER + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PAYS + " TEXT,"
            + COLUMN_VILLE + " TEXT,"
            + COLUMN_JOBS + " TEXT"
            + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FACTURES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement upgrade logic here
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACTURES);
        onCreate(db);
    }

    public boolean addInfoPersonnelle(InfoPersonnelle infoPersonnelle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValues(infoPersonnelle);

        // Insert or replace Facture
        long result = db.insertWithOnConflict(TABLE_FACTURES, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        // Close the database
        db.close();

        // Check if insertion was successful
        boolean isSuccess = result != -1;
        return isSuccess;
    }

    private ContentValues populateContentValues(InfoPersonnelle infoPersonnelle) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_OF_USERS, infoPersonnelle.getLogoOfUser());
        values.put(COLUMN_FIRST_NAME, infoPersonnelle.getF_name());
        values.put(COLUMN_LAST_NAME, infoPersonnelle.getL_name());
        values.put(COLUMN_PHONE_NUMBER, infoPersonnelle.getN_phone());
        values.put(COLUMN_EMAIL, infoPersonnelle.getEmail());
        values.put(COLUMN_PAYS, infoPersonnelle.getPays());
        values.put(COLUMN_VILLE, infoPersonnelle.getVille());
        values.put(COLUMN_JOBS, infoPersonnelle.getJob());
        return values;
    }

    public ArrayList<InfoPersonnelle> getInfo() {
        ArrayList<InfoPersonnelle> infoPersonnelles = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FACTURES;

        SQLiteDatabase db = this.getWritableDatabase();
        try (Cursor cursor = db.rawQuery(selectQuery, null)) {
            while (cursor.moveToNext()) {
                infoPersonnelles.add(createInfoPersonnelleFromCursor(cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return infoPersonnelles;
    }

    private InfoPersonnelle createInfoPersonnelleFromCursor(Cursor cursor) {
        InfoPersonnelle infoPersonnelle = new InfoPersonnelle();
        infoPersonnelle.setLogoOfUser(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_OF_USERS)));
        infoPersonnelle.setF_name(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)));
        infoPersonnelle.setL_name(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)));
        infoPersonnelle.setN_phone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER)));
        infoPersonnelle.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
        infoPersonnelle.setPays(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAYS)));
        infoPersonnelle.setVille(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VILLE)));
        infoPersonnelle.setJob(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOBS)));
        return infoPersonnelle;
    }

    public boolean isDatabaseEmpty() {
        boolean isEmpty = true;
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_FACTURES;
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
    public void clearAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FACTURES);
        db.close();
    }
}
