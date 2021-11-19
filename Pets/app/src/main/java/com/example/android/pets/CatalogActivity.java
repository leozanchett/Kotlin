package com.example.android.pets;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.pets.data.PetQueryes;
import com.example.android.pets.data.PetsContract;

import java.util.List;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private PetCursorAdapter adapter;
    private static final int PET_LOADER = 0;

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
        ListView petListView = (ListView) findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);
        adapter = new PetCursorAdapter(this, null);
        petListView.setAdapter(adapter);
        petListView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
            Uri contenPetUri = ContentUris.withAppendedId(PetsContract.PetsUri.CONTENT_URI, id);
            intent.setData(contenPetUri);
            startActivity(intent);
        });

        getLoaderManager().initLoader(PET_LOADER, null, this);
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
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                getContentResolver().delete(PetsContract.PetsUri.CONTENT_URI, null, null);
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        return new CursorLoader(
                this,
                PetsContract.PetsUri.CONTENT_URI,
                PetQueryes.allPetInfo(),
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}