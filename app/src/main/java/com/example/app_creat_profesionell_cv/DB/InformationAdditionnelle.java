package com.example.app_creat_profesionell_cv.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.app_creat_profesionell_cv.Classes.Competance;
import com.example.app_creat_profesionell_cv.Classes.InfoAdditionnelle;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class InformationAdditionnelle extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "information_additionnelle";

    // Table name
    private static final String TABLE_INFO = "info";

    // Column names
    private static final String COLUMN_COMPETENCE = "competence";
    private static final String COLUMN_SOFTSKILL = "softSkills";
    private static final String COLUMN_LANGUE = "langue";
    private static final String COLUMN_LOISIR = "loisir";
    private static final String COLUMN_CERTIFICATE = "certificate";
    private static final String COLUMN_LINKEDIN = "linkedin";
    private static final String COLUMN_GITHUB = "github";
    private static final String COLUMN_LEETCODE = "leetCode";

    private static final String CREATE_INFO_TABLE = "CREATE TABLE " + TABLE_INFO + "("
            + COLUMN_COMPETENCE + " TEXT,"
            + COLUMN_SOFTSKILL + " TEXT,"
            + COLUMN_LANGUE + " TEXT,"
            + COLUMN_LOISIR + " TEXT,"
            + COLUMN_CERTIFICATE + "TEXT,"
            + COLUMN_LINKEDIN + " TEXT,"
            + COLUMN_GITHUB + " TEXT,"
            + COLUMN_LEETCODE + " TEXT"
            + ")";

    public InformationAdditionnelle(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE info (" +
                "softSkills TEXT, " +
                "competence TEXT, " +
                "github TEXT, " +
                "leetCode TEXT, " +
                "certificate TEXT, " +
                "langue TEXT, " +
                "linkedin TEXT, " +
                "loisir TEXT);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("ALTER TABLE info ADD COLUMN certificate TEXT;");
        }
    }


    public boolean addInformationAdditionnelle(InfoAdditionnelle infoAdditionnelle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValues(infoAdditionnelle);

        // Insert or replace project info
        long result = db.insertWithOnConflict(TABLE_INFO, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        // Close the database
        db.close();

        // Check if insertion was successful
        return result != -1;
    }

    private ContentValues populateContentValues(InfoAdditionnelle infoAdditionnelle) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPETENCE, convertArrayListToJson(infoAdditionnelle.getCompetanceArrayList()));
        values.put(COLUMN_SOFTSKILL, convertArrayListToJson(infoAdditionnelle.getSoftSkillsArrayList()));
        values.put(COLUMN_LANGUE, convertArrayListToJson(infoAdditionnelle.getLangueArrayList()));
        values.put(COLUMN_LOISIR, convertArrayListToJson(infoAdditionnelle.getLoisirArrayList()));
        values.put(COLUMN_CERTIFICATE, convertArrayListToJson(infoAdditionnelle.getCertificateArrayList()));
        values.put(COLUMN_LINKEDIN, infoAdditionnelle.getLinkdin());
        values.put(COLUMN_GITHUB, infoAdditionnelle.getGithub());
        values.put(COLUMN_LEETCODE, infoAdditionnelle.getLeetCode());
        return values;
    }

    private String convertArrayListToJson(ArrayList<?> list) {
        return new JSONArray(list).toString();
    }

    private ArrayList<String> convertJsonToArrayList(String json) {
        ArrayList<String> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<InfoAdditionnelle> getInfoAdditionnelle() {
        ArrayList<InfoAdditionnelle> infoAdditionnelles = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_INFO;

        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.rawQuery(selectQuery, null)) {
            while (cursor.moveToNext()) {
                infoAdditionnelles.add(createInfoAdditionnelleFromCursor(cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return infoAdditionnelles;
    }

    @SuppressLint("Range")
    private InfoAdditionnelle createInfoAdditionnelleFromCursor(Cursor cursor) {
        InfoAdditionnelle infoAdditionnelle = new InfoAdditionnelle();
        infoAdditionnelle.setCompetanceArrayList(convertJsonToArrayList(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMPETENCE))));
        infoAdditionnelle.setSoftSkillsArrayList(convertJsonToArrayList(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOFTSKILL))));
        infoAdditionnelle.setLangueArrayList(convertJsonToArrayList(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANGUE))));
        infoAdditionnelle.setLoisirArrayList(convertJsonToArrayList(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOISIR))));
        infoAdditionnelle.setCertificateArrayList(convertJsonToArrayList(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CERTIFICATE))));
        infoAdditionnelle.setLinkdin(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINKEDIN)));
        infoAdditionnelle.setGithub(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GITHUB)));
        infoAdditionnelle.setLeetCode(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LEETCODE)));
        return infoAdditionnelle;
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
