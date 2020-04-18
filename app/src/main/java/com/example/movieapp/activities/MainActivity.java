package com.example.movieapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.adapters.FilmAdapter;
import com.example.movieapp.database.FilmDB;
import com.example.movieapp.database.FilmTableHelper;
import com.example.movieapp.models.Film;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
        filmDB = new FilmDB(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        database = filmDB.getReadableDatabase();

        if(database != null) {
            loadFilms();
        }else{
            Toast.makeText(this,"qualcosa è andato storto",Toast.LENGTH_LONG).show();
        }
    }

    private void loadFilms() {
        filmItems = database.query(tableName, null, null, null,
                null, null, FilmTableHelper.TITLE);

        if (filmItems != null) {
            if (filmAdapter == null) {
                filmAdapter = new FilmAdapter(this, filmItems);
                filmList.setAdapter(filmAdapter);
            } else {
                filmAdapter.changeCursor(filmItems);
                filmAdapter.notifyDataSetChanged();
            }
        }
    }


}
