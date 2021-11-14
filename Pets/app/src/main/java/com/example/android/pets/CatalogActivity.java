package com.example.android.pets;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.pets.data.PetsContract;
import com.example.android.pets.data.PetsDBHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    private PetsDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        mDbHelper = new PetsDBHelper(this);
        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
            startActivity(intent);
        });

        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    @SuppressLint("SetTextI18n")
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        String[] colunas = new String[]{
                PetsContract.PetsEntry._ID,
                PetsContract.PetsEntry.COLUMN_PET_NAME,
                PetsContract.PetsEntry.COLUMN_PET_BREED,
                PetsContract.PetsEntry.COLUMN_PET_WEIGTH,
                PetsContract.PetsEntry.COLUMN_PET_GENDER,
        };
        //String orderBy = PetsContract.PetsEntry._ID + " DESC";
        //orderBy,

        try (Cursor cursor = db.query(
                PetsContract.PetsEntry.TABLE_NAME,
                colunas,
                null,
                null,
                null,
                null,
                null, //orderBy,
                null
        )) {
            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            displayView.setText("Pets cadastrados: " + cursor.getCount());
            StringBuilder colunasDoArray = new StringBuilder();
            for (String val : colunas) {
                colunasDoArray.append(val).append(" - ");
            }
            displayView.append("\n" + colunasDoArray);
            while (cursor.moveToNext()) {
                displayView.append(("\n" + getCursorString(cursor)));
            }
        }
        // Always close the cursor when you're done reading from it. This releases all its
        // resources and makes it invalid.
    }

    private String getCursorString(@NonNull Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex(PetsContract.PetsEntry._ID)) + '-' +
                cursor.getString(cursor.getColumnIndex(PetsContract.PetsEntry.COLUMN_PET_NAME)) + '-' +
                cursor.getString(cursor.getColumnIndex(PetsContract.PetsEntry.COLUMN_PET_BREED)) + '-' +
                cursor.getInt(cursor.getColumnIndex(PetsContract.PetsEntry.COLUMN_PET_WEIGTH)) + '-' +
                cursor.getInt(cursor.getColumnIndex(PetsContract.PetsEntry.COLUMN_PET_GENDER));
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                long idNovoPet = insertPet();
                Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Id do novo pet inserido " + idNovoPet, Snackbar.LENGTH_LONG).show();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private long insertPet() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PetsContract.PetsEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetsContract.PetsEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetsContract.PetsEntry.COLUMN_PET_WEIGTH, 7);
        values.put(PetsContract.PetsEntry.COLUMN_PET_GENDER, PetsContract.PetsEntry.GENDER_PET_MALE);
        return db.insert(PetsContract.PetsEntry.TABLE_NAME, null, values);

    }
}