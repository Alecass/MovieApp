package com.example.movieapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.adapters.FilmAdapter;
import com.example.movieapp.database.FilmDB;
import com.example.movieapp.database.FilmProvider;
import com.example.movieapp.database.FilmTableHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MY_ID = 1;

    final String tableName = FilmTableHelper.TABLE_NAME;

    FilmAdapter filmAdapter;

    FilmDB filmDB;
    SQLiteDatabase database;

    Cursor filmItems;
    GridView filmList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filmList = findViewById(R.id.filmGrid);
        filmAdapter = new FilmAdapter(this,null);
        filmList.setAdapter(filmAdapter);
        getSupportLoaderManager().initLoader(MY_ID, null, this);
        insertFilm();
    }
    private void insertFilm(){
        ContentValues values = new ContentValues();
        values.put(FilmTableHelper.TITLE,"film1");
        Uri filmUri = getContentResolver().insert(FilmProvider.FILMS_URI,values);
        Toast.makeText(this, "Created Contact " + "film1", Toast.LENGTH_LONG).show();
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, FilmProvider.FILMS_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        filmAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        filmAdapter.changeCursor(null);
    }
}
