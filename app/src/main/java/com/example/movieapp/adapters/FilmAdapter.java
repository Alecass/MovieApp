package com.example.movieapp.adapters;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.database.FilmTableHelper;


public class FilmAdapter extends CursorAdapter {

    public static String THUMBNAIL_BASE_URL = "https://image.tmdb.org/t/p/original";

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
        String description = cursor.getString(cursor.getColumnIndex(FilmTableHelper.DESCRIPTION));

        String imagePath = cursor.getString(cursor.getColumnIndex(FilmTableHelper.IMAGE_PATH));

        ImageView imageView;

        imageView = view.findViewById(R.id.filmImage);

        Glide.with(view).load(THUMBNAIL_BASE_URL+imagePath).placeholder(R.drawable.ic_launcher_foreground).into(imageView);
    }
}
