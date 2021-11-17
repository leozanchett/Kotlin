package com.example.android.pets;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pets.data.PetsContract;

public class PetCursorAdapter extends CursorAdapter {

    public PetCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvName = (TextView) view.findViewById(R.id.name);
        TextView tvSummary = (TextView) view.findViewById(R.id.summary);
        // Extract properties from cursor
        String body = cursor.getString(cursor.getColumnIndexOrThrow(PetsContract.PetsEntry.COLUMN_PET_NAME));
        String summary = cursor.getString(cursor.getColumnIndexOrThrow(PetsContract.PetsEntry.COLUMN_PET_BREED));
        // Populate fields with extracted properties
        tvName.setText(body);
        tvSummary.setText(summary);
    }
}
