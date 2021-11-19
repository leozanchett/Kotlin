package com.example.android.pets.data;

public class PetQueryes {

    public static final String[] allPetInfo(){
        return new String[]{
                PetsContract.PetsEntry._ID,
                PetsContract.PetsEntry.COLUMN_PET_NAME,
                PetsContract.PetsEntry.COLUMN_PET_BREED,
                PetsContract.PetsEntry.COLUMN_PET_WEIGTH,
                PetsContract.PetsEntry.COLUMN_PET_GENDER,
        };
    }
}
