package com.example.movieapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.adapters.FilmAdapter;
import com.example.movieapp.api.JsonFilmsApi;
import com.example.movieapp.database.FilmDB;
import com.example.movieapp.database.FilmProvider;
import com.example.movieapp.database.FilmTableHelper;
import com.example.movieapp.models.ApiResponse;
import com.example.movieapp.models.Film;
import com.example.movieapp.services.ApiListener;
import com.example.movieapp.services.ApiService;
import com.example.movieapp.services.ApiWebService;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MY_ID = 1;

    final String tableName = FilmTableHelper.TABLE_NAME;

    FilmAdapter filmAdapter;

    public static Locale locale;

    FilmDB filmDB;
    SQLiteDatabase database;

    Cursor filmItems;
    GridView filmList;

    List<Film> films;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locale = getResources().getConfiguration().locale;

        filmList = findViewById(R.id.filmGrid);
        filmAdapter = new FilmAdapter(this,null);
        filmList.setAdapter(filmAdapter);
        getSupportLoaderManager().initLoader(MY_ID, null, this);
        //retrieve data from the server using retrofit
        //getFilms();
        apiService = ApiService.getInstance();

        ApiListener apiListener = new ApiListener(){
            @Override
            public void onFilmsLoaded(boolean success, ApiResponse apiResponse, int errorCode, String errorMessage) {
                if (success) {
                    films = apiResponse.getFilms();

                    ContentValues values = new ContentValues();
                    for (Film film : films) {
                        values.put(FilmTableHelper.TITLE, film.getTitle());

                        getContentResolver().insert(FilmProvider.FILMS_URI, values);
                    }}
            }
        };

        apiService.getMovies(apiListener);
    }


    private void insertFilm(){
        ContentValues values = new ContentValues();
        values.put(FilmTableHelper.TITLE,"film1");
        Uri filmUri = getContentResolver().insert(FilmProvider.FILMS_URI,values);
        Toast.makeText(this, "Created Contact " + "film1", Toast.LENGTH_LONG).show();
    }

    private void deleteAll(){
        getContentResolver().delete(FilmProvider.FILMS_URI,null,null);
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
