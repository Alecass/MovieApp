package com.example.movieapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.adapters.FilmAdapter;
import com.example.movieapp.database.FilmDB;
import com.example.movieapp.database.FilmProvider;
import com.example.movieapp.database.FilmTableHelper;
import com.example.movieapp.fragments.ConfirmDialogFragment;
import com.example.movieapp.fragments.ConfirmDialogFragmentListener;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ConfirmDialogFragmentListener {

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

    MenuItem appBarFavorite;
    MenuItem appBarSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locale = getResources().getConfiguration().locale;

        filmList = findViewById(R.id.filmGrid);
        filmAdapter = new FilmAdapter(this, null);
        filmList.setAdapter(filmAdapter);
        getSupportLoaderManager().initLoader(MY_ID, null, this);
        //retrieve data from the server using retrofit
        //getFilms();
        apiService = ApiService.getInstance();
        filmDB = new FilmDB(this);
        database = filmDB.getReadableDatabase();

        ApiListener apiListener = new ApiListener() {
            @Override
            public void onFilmsLoaded(boolean success, ApiResponse apiResponse, int errorCode, String errorMessage) {
                if (success) {
                    films = apiResponse.getFilms();
                    ContentValues values = new ContentValues();
                    for (Film film : films) {
                        //check if film already exist or not
                        Cursor c=database.rawQuery("SELECT * FROM films WHERE image_path='"+film.getImagePath()+"'", null);
                        if(! c.moveToFirst()){
                            c.close();
                            values.put(FilmTableHelper.TITLE, film.getTitle());
                            values.put(FilmTableHelper.IMAGE_PATH, film.getImagePath());
                            values.put(FilmTableHelper.DESCRIPTION, film.getDescription());
                            values.put(FilmTableHelper.RATING, film.getRating());

                            getContentResolver().insert(FilmProvider.FILMS_URI, values);
                        }
                    }
                    filmAdapter.notifyDataSetChanged();
                }
            }
        };
        apiService.getMovies(apiListener);


        filmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        filmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                ConfirmDialogFragment dialogFragment = new ConfirmDialogFragment(getString(R.string.add_to_favourite_title),
                        getString(R.string.add_to_favourite_message),
                        id);
                dialogFragment.show(fragmentManager, ConfirmDialogFragment.class.getName());
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);

        appBarFavorite = menu.getItem(1);
        appBarFavorite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent favoritePage = new Intent(MainActivity.this,FavoriteActivity.class);
                startActivity(favoritePage);
                return false;
            }
        });

        appBarSearch = menu.getItem(0);
        appBarSearch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });

        return true;
    };

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

    @Override
    public void onPositivePressed(long id) {

        if (id > 0) {

            String whereClause = FilmTableHelper._ID + "=?";
            String[] whereArgs = new String[]{String.valueOf(id)};

            ContentValues values = new ContentValues();
            values.put(FilmTableHelper.IS_FAVOURITE, true);

            int favourite = database.update(tableName, values, whereClause, whereArgs);

            if (favourite > 0) {
                Toast.makeText(MainActivity.this,R.string.add_to_favourite_success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, R.string.add_to_favourite_error, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onNegativePressed() {

    }
}
