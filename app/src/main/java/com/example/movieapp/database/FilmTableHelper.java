package com.example.movieapp.database;

import android.provider.BaseColumns;

public class FilmTableHelper implements BaseColumns {
    public static final String TABLE_NAME = "films";
    public static final String TITLE = "title";
    public static final String IMAGE_PATH = "image_path";
    public static final String DESCRIPTION = "description";
    public static final String IS_FAVOURITE = "is_favourite";

    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            TITLE + " TEXT , " +
            IMAGE_PATH + " TEXT , " +
            DESCRIPTION + " TEXT , " +
            IS_FAVOURITE + " INTEGER DEFAULT 0 ) ;";
}
