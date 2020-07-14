package com.example.movieapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.adapters.FilmAdapter;
import com.example.movieapp.database.FilmDB;
import com.example.movieapp.database.FilmProvider;
import com.example.movieapp.database.FilmTableHelper;
import com.example.movieapp.fragments.ConfirmDialogFragment;
import com.example.movieapp.fragments.ConfirmDialogFragmentListener;
import com.example.movieapp.models.Film;

public class FavoriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> , ConfirmDialogFragmentListener {

    FilmAdapter filmAdapter;
    GridView filmList;
    TextView noFavorite;
    private static final int MY_ID = 2;

    FilmDB filmDB;
    SQLiteDatabase database;

    final String tableName = FilmTableHelper.TABLE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        setTitle("Favorite Movies");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        filmDB = new FilmDB(this);
        database = filmDB.getWritableDatabase();

        noFavorite = findViewById(R.id.no_favorites);

        filmList = findViewById(R.id.favoriteFilmGrid);
        filmAdapter = new FilmAdapter(this, null);
        filmList.setAdapter(filmAdapter);
        getSupportLoaderManager().initLoader(MY_ID, null, this);

        filmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavoriteActivity.this,DetailActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        filmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                ConfirmDialogFragment dialogFragment = new ConfirmDialogFragment(getString(R.string.delete_favourite_title),
                        getString(R.string.delete_favourite_message),
                        id);
                dialogFragment.show(fragmentManager, ConfirmDialogFragment.class.getName());
                return true;
            }
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, FilmProvider.FILMS_URI, null, FilmTableHelper.IS_FAVOURITE ,null , null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        filmAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        filmAdapter.swapCursor(null);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportLoaderManager().restartLoader(0, null, this);

        Cursor c = database.rawQuery("SELECT * FROM films WHERE is_favourite=1", null);

        if(c.isAfterLast()){
            noFavorite.setVisibility(View.VISIBLE);
        }else{
            noFavorite.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPositivePressed(long id) {

        if (id > 0) {
            String whereClause = FilmTableHelper._ID + "=?";
            String[] whereArgs = new String[]{String.valueOf(id)};

            ContentValues values = new ContentValues();
            values.put(FilmTableHelper.IS_FAVOURITE, false);


            database.update(tableName, values, whereClause, whereArgs);
            noFavorite.setVisibility(View.VISIBLE);
            getSupportLoaderManager().restartLoader(0, null, this);
        }
    }

    @Override
    public void onNegativePressed(){
    }
}
