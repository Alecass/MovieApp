package com.example.movieapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.database.FilmProvider;
import com.example.movieapp.database.FilmTableHelper;

public class DetailActivity extends AppCompatActivity {

    ImageView detailImage;
    TextView detailTitle;
    TextView detailDescription;
    long attivita_id;

    public static String THUMBNAIL_BASE_URL = "https://image.tmdb.org/t/p/original";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Movie Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detailImage = findViewById(R.id.detailImage);
        detailTitle = findViewById(R.id.detailTitle);
        detailDescription = findViewById(R.id.detailDescription);
        attivita_id = getIntent().getLongExtra("id", 0);

       Cursor cursor = getContentResolver().query(FilmProvider.FILMS_URI, null, FilmTableHelper._ID + " =" + attivita_id, null, null);
       cursor.moveToNext();

       detailTitle.setText(cursor.getString(cursor.getColumnIndex(FilmTableHelper.TITLE)));
       detailDescription.setText(cursor.getString(cursor.getColumnIndex(FilmTableHelper.DESCRIPTION)));

       //image url
        String imagePath = cursor.getString(cursor.getColumnIndex(FilmTableHelper.IMAGE_PATH));
        Glide.with(this).load(THUMBNAIL_BASE_URL+imagePath).placeholder(R.drawable.ic_launcher_foreground).centerCrop().into(detailImage);

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
