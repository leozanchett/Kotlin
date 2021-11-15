package com.example.android.pets;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.pets.data.PetsContract;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
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

        String[] projection = new String[]{
                PetsContract.PetsEntry._ID,
                PetsContract.PetsEntry.COLUMN_PET_NAME,
                PetsContract.PetsEntry.COLUMN_PET_BREED,
                PetsContract.PetsEntry.COLUMN_PET_WEIGTH,
                PetsContract.PetsEntry.COLUMN_PET_GENDER,
        };

        try (Cursor cursor = getContentResolver().query(
                PetsContract.PetsUri.CONTENT_URI,
                projection,
                null,
                null,
                null
        )) {
            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            if (cursor != null) {
                displayView.setText("Pets cadastrados: " + cursor.getCount());
                StringBuilder colunasDoArray = new StringBuilder();
                for (String val : projection) {
                    colunasDoArray.append(val).append(" - ");
                }
                displayView.append("\n" + colunasDoArray);
                while (cursor.moveToNext()) {
                    displayView.append(("\n" + getCursorString(cursor)));
                }
            } else {
                displayView.setText("Nenhum pet cadastrado");
            }
        }
    }

    private String getCursorString(@NonNull Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex(PetsContract.PetsEntry._ID)) + "    " +
                cursor.getString(cursor.getColumnIndex(PetsContract.PetsEntry.COLUMN_PET_NAME)) + "    " +
                cursor.getString(cursor.getColumnIndex(PetsContract.PetsEntry.COLUMN_PET_BREED)) + "    " +
                cursor.getInt(cursor.getColumnIndex(PetsContract.PetsEntry.COLUMN_PET_WEIGTH)) + "    " +
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
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertPet() {
        ContentValues values = new ContentValues();
        values.put(PetsContract.PetsEntry.COLUMN_PET_NAME, "Pet default");
        values.put(PetsContract.PetsEntry.COLUMN_PET_BREED, "Raça default");
        values.put(PetsContract.PetsEntry.COLUMN_PET_WEIGTH, 1);
        values.put(PetsContract.PetsEntry.COLUMN_PET_GENDER, PetsContract.PetsEntry.GENDER_PET_MALE);
        Uri uri = getContentResolver().insert(PetsContract.PetsUri.CONTENT_URI, values);
        final long idPetCadastrado = ContentUris.parseId(uri);
        if (idPetCadastrado > 0) {
            Toast.makeText(this, "Pet default cadastrado com o id " + idPetCadastrado, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Houve um problema no cadastro do pet default", Toast.LENGTH_SHORT).show();
        }

    }
}