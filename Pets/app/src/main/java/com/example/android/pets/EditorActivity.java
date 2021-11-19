/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.pets.data.PetQueryes;
import com.example.android.pets.data.PetsContract;
import com.example.android.pets.data.PetsContract.PetsEntry;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mBreedEditText;
    private EditText mWeightEditText;
    private Spinner mGenderSpinner;
    private int mGender = 0;
    private Uri currentPetUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        setupSpinner();

        Intent intent = getIntent();
        currentPetUri = intent.getData();
        if (currentPetUri == null) {
            setTitle(getString(R.string.editor_activity_title_new_pet));
        } else {
            setTitle(getString(R.string.editor_activity_title_edit_pet));
            final String selection = PetsEntry._ID + " = ?";
            try (Cursor cursor = getContentResolver().query(
                    currentPetUri,
                    PetQueryes.allPetInfo(),
                    selection,
                    null,
                    null,
                    null
            )) {
                cursor.moveToFirst();
                final String nomePet = cursor.getString(cursor.getColumnIndex(PetsEntry.COLUMN_PET_NAME));
                final String breedPet = cursor.getString(cursor.getColumnIndex(PetsEntry.COLUMN_PET_BREED));
                final int genderPet = cursor.getInt(cursor.getColumnIndex(PetsEntry.COLUMN_PET_GENDER));
                final String weigth = cursor.getString(cursor.getColumnIndex(PetsEntry.COLUMN_PET_WEIGTH));
                mNameEditText.setText(nomePet);
                mBreedEditText.setText(breedPet);
                mGenderSpinner.setSelection(genderPet);
                mWeightEditText.setText(weigth);
            }
        }

    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetsEntry.GENDER_PET_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PetsEntry.GENDER_PET_FEMALE;
                    } else {
                        mGender = PetsEntry.GENDER_PET_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = PetsEntry.GENDER_PET_UNKNOWN;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                UpdateOrInsert();
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UpdateOrInsert() {
        String msg;
        boolean statusOperacao;
        ContentValues values = new ContentValues();
        values.put(PetsContract.PetsEntry.COLUMN_PET_NAME, mNameEditText.getText().toString().trim());
        values.put(PetsContract.PetsEntry.COLUMN_PET_BREED, mBreedEditText.getText().toString().trim());
        values.put(PetsContract.PetsEntry.COLUMN_PET_WEIGTH, Integer.valueOf(mWeightEditText.getText().toString().trim()));
        values.put(PetsContract.PetsEntry.COLUMN_PET_GENDER, mGenderSpinner.getSelectedItemPosition());
        if (currentPetUri == null) {
            Uri uri = getContentResolver().insert(PetsContract.PetsUri.CONTENT_URI, values);
            final long idPetCadastrado = ContentUris.parseId(uri);
            if (idPetCadastrado > 0) {
                msg = " cadastrado com o id " + idPetCadastrado;
                statusOperacao = true;
            } else {
                msg = " no cadastro do pet";
                statusOperacao = false;
            }
        } else {
            int idAlteracao = getContentResolver().update(currentPetUri, values, null, null);
            if (idAlteracao > 0) {
                msg = " atualizado com sucesso";
                statusOperacao = true;
            } else {
                msg = " ao atualizar o pet";
                statusOperacao = false;
            }
        }
        if (statusOperacao) {
            Toast.makeText(this, mNameEditText.getText().toString().trim() + msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Houve um problema" + msg + mNameEditText.getText().toString().trim(), Toast.LENGTH_SHORT).show();
        }

    }
}