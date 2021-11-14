package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.pets.CatalogActivity;
import com.example.android.pets.EditorActivity;
import com.example.android.pets.data.PetsContract.PetsEntry;

public class PetsDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = PetsDBHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "abrigo_animais.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;


    private final static String TABLE_PETS_CREATE = "CREATE TABLE "+ PetsEntry.TABLE_NAME+" ( " +
                PetsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                PetsEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "+
                PetsEntry.COLUMN_PET_BREED + " TEXT, "+
                PetsEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "+
                PetsEntry.COLUMN_PET_WEIGTH + " INTEGER NOT NULL DEFAULT 0); ";


    public PetsDBHelper(CatalogActivity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public PetsDBHelper(EditorActivity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public PetsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_PETS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
