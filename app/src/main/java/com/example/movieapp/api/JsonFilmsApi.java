package com.example.movieapp.api;

import retrofit2.Call;

import com.example.movieapp.models.Film;

import java.util.List;

import retrofit2.http.GET;

public interface JsonFilmsApi {

    @GET("10?page=1&api_key=c09e9517fddb8b0a0570447c30a25fac&sort_by=title.asc")
    Call<List<Film>> getFilms();
}
