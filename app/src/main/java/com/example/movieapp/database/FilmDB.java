package com.example.movieapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FilmDB extends SQLiteOpenHelper {


    private static final String DB_NAME = "films.db";
    private static final int VERSION = 1;

    public FilmDB(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FilmTableHelper.CREATE);

        for(int i=0; i <100;i++ ){
            ContentValues vValues = new ContentValues();

            String title = "title" + i;
            vValues.put(FilmTableHelper.TITLE,title);

            String imagePath = "path" + i;
            vValues.put(FilmTableHelper.IMAGE_PATH,imagePath);

            String description = "description" + i;
            vValues.put(FilmTableHelper.DESCRIPTION,description);

            vValues.put(FilmTableHelper.IS_FAVOURITE,1);

            db.insert(FilmTableHelper.TABLE_NAME, null,vValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
