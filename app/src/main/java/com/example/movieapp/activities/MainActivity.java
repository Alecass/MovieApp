package com.example.movieapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.adapters.FilmAdapter;
import com.example.movieapp.database.FilmDB;
import com.example.movieapp.database.FilmProvider;
import com.example.movieapp.database.FilmTableHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MY_ID = 1;

    final String tableName = FilmTableHelper.TABLE_NAME;

    FilmAdapter filmAdapter;

    FilmDB filmDB;
    SQLiteDatabase database;

    Cursor filmItems;
    ListView filmList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filmList = findViewById(R.id.list_view);
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
