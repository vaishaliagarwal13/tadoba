package com.woodpeckers.tadoba;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaishaliagarwal on 14/07/16.
 */


public class BirdDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "TadobaBird.db";


    private static final String CREATE_FAMILY_TABLE = "CREATE TABLE " + BirdContract.BirdFamilyEntry.TABLE_NAME + "("
            + BirdContract.BirdFamilyEntry.COLUMN_ID + " TEXT PRIMARY KEY ,"
            + BirdContract.BirdFamilyEntry.COLUMN_LATIN_NAME + " TEXT,"
            + BirdContract.BirdFamilyEntry.COLUMN_COMMON_NAME + " TEXT,"
            + BirdContract.BirdFamilyEntry.COLUMN_FAMILY_PIC + " TEXT" + ")";

    private static final String CREATE_BIRD_TABLE = "CREATE TABLE " + BirdContract.BirdEntry.TABLE_NAME + "("
            + BirdContract.BirdEntry.COLUMN_ID + " TEXT PRIMARY KEY  ,"
            + BirdContract.BirdEntry.COLUMN_FAMILY_KEY + " TEXT,"
            + BirdContract.BirdEntry.COLUMN_LATIN_NAME + " TEXT,"
            + BirdContract.BirdEntry.COLUMN_COMMON_NAME + " TEXT,"
            + BirdContract.BirdEntry.COLUMN_BIRD_IMAGES + " TEXT,"
            + BirdContract.BirdEntry.COLUMN_IS_VIEWED + " INTEGER,"
            + BirdContract.BirdEntry.COLUMN_VIEWED_DATE_TIME + " LONG,"
            + " FOREIGN KEY (" + BirdContract.BirdEntry.COLUMN_FAMILY_KEY + ") REFERENCES "
            + BirdContract.BirdFamilyEntry.TABLE_NAME + " (" + BirdContract.BirdFamilyEntry.COLUMN_ID + ") "
            + ")";

    private static final String GET_ALL_FAMILIES = "SELECT  * FROM " + BirdContract.BirdFamilyEntry.TABLE_NAME;

    public BirdDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FAMILY_TABLE);
        db.execSQL(CREATE_BIRD_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + BirdContract.BirdEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BirdContract.BirdFamilyEntry.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }


    @NonNull
    public static ContentValues getBirdFamilyContentValues(BirdFamily family) {
        ContentValues values = new ContentValues();
        values.put(BirdContract.BirdFamilyEntry.COLUMN_FAMILY_PIC, family.getFamilyImage());
        values.put(BirdContract.BirdFamilyEntry.COLUMN_COMMON_NAME, family.getCommonName());
        values.put(BirdContract.BirdFamilyEntry.COLUMN_LATIN_NAME, family.getLatinName());
        values.put(BirdContract.BirdFamilyEntry.COLUMN_ID, family.getFamilyID());
        return values;
    }

    @NonNull
    public static ContentValues getBirdContentValues(String familyID, Bird bird) {
        ContentValues values = new ContentValues();
        values.put(BirdContract.BirdEntry.COLUMN_ID, bird.getId());
        values.put(BirdContract.BirdEntry.COLUMN_FAMILY_KEY, familyID);
        values.put(BirdContract.BirdEntry.COLUMN_LATIN_NAME, bird.getLatinName());
        values.put(BirdContract.BirdEntry.COLUMN_COMMON_NAME, bird.getCommonName());
        values.put(BirdContract.BirdEntry.COLUMN_BIRD_IMAGES, Bird.convertImageListToString(bird.getImageList()));
        values.put(BirdContract.BirdEntry.COLUMN_IS_VIEWED, bird.isViewed());
        values.put(BirdContract.BirdEntry.COLUMN_VIEWED_DATE_TIME, (bird.getViewedDateTime()));
        return values;
    }

}