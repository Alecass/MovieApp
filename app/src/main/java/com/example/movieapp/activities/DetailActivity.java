package com.example.movieapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.database.FilmDB;
import com.example.movieapp.database.FilmProvider;
import com.example.movieapp.database.FilmTableHelper;

public class DetailActivity extends AppCompatActivity {

    ImageView detailImage;
    ImageView favoriteImage;
    TextView detailTitle;
    TextView detailDescription;
    RatingBar ratingBar;
    long attivita_id;
    boolean isFavourite;

    FilmDB filmDB;
    SQLiteDatabase database;
    final String tableName = FilmTableHelper.TABLE_NAME;

    public static String THUMBNAIL_BASE_URL = "https://image.tmdb.org/t/p/original";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("Movie Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ratingBar = findViewById(R.id.ratingBar);
        favoriteImage = findViewById(R.id.favoriteImageBtn);
        detailImage = findViewById(R.id.detailImage);
        detailTitle = findViewById(R.id.detailTitle);
        detailDescription = findViewById(R.id.detailDescription);
        attivita_id = getIntent().getLongExtra("id", 0);

       Cursor cursor = getContentResolver().query(FilmProvider.FILMS_URI, null, FilmTableHelper._ID + " =" + attivita_id, null, null);
       cursor.moveToNext();

       detailTitle.setText(cursor.getString(cursor.getColumnIndex(FilmTableHelper.TITLE)));
       detailDescription.setText(cursor.getString(cursor.getColumnIndex(FilmTableHelper.DESCRIPTION)));

       ratingBar.setRating(cursor.getFloat(cursor.getColumnIndex(FilmTableHelper.RATING)) / 2);

       //image url
        final String imagePath = cursor.getString(cursor.getColumnIndex(FilmTableHelper.IMAGE_PATH));

        Glide.with(this)
                .load(THUMBNAIL_BASE_URL+imagePath)
                .placeholder(R.drawable.ic_launcher_foreground)
                .centerCrop().into(detailImage);

        if(cursor.getInt(cursor.getColumnIndex(FilmTableHelper.IS_FAVOURITE)) == 1){
            isFavourite = true;
        }else{
            isFavourite = false;
        }

        filmDB = new FilmDB(DetailActivity.this);
        database = filmDB.getWritableDatabase();

        favoriteImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String whereClause = FilmTableHelper._ID + "=?";
                String[] whereArgs = new String[]{String.valueOf(attivita_id)};

                ContentValues values = new ContentValues();

                if (isFavourite){

                    isFavourite = false;
                    values.put(FilmTableHelper.IS_FAVOURITE, false);

                    database.update(tableName, values, whereClause, whereArgs);
                    favoriteImage.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));

                } else {

                    isFavourite = true;
                    values.put(FilmTableHelper.IS_FAVOURITE, true);

                    database.update(tableName, values, whereClause, whereArgs);
                    favoriteImage.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        });

    }

    @Override
    protected void onResume() {

        if(isFavourite){
            favoriteImage.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            favoriteImage.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
        }
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
