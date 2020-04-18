package com.example.movieapp.adapters;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.database.FilmTableHelper;


public class FilmAdapter extends CursorAdapter {
    public FilmAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater vInflater = LayoutInflater.from(context);
       View vView = vInflater.inflate(R.layout.list_item,null);
       return vView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(FilmTableHelper.TITLE));
        TextView filmTitle = view.findViewById(R.id.filmTitle);
        filmTitle.setText(title);
    }
}
