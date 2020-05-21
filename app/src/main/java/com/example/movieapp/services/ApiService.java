package com.example.movieapp.services;

import android.util.Log;

import com.example.movieapp.activities.MainActivity;
import com.example.movieapp.models.ApiResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static ApiService instance;

    private String MOVIES_BASE_URL = "https://api.themoviedb.org/";
    private String API_KEY = "c09e9517fddb8b0a0570447c30a25fac";
    private String LANGUAGE = MainActivity.locale.toString();
    private String PAGE = "1";
    private String REGION = MainActivity.locale.getCountry();
    private String VERSION = "3";
    private String CATEGORY = "popular";

    private ApiWebService apiWebService;

    private ApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MOVIES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiWebService = retrofit.create(ApiWebService.class);
    }

    public static ApiService getInstance() {
        if (instance == null)
            instance = new ApiService();
        return instance;
    }

    public void getMovies(final ApiListener listener) {
        Call<ApiResponse> response = apiWebService.getResponse(VERSION, CATEGORY, API_KEY, LANGUAGE, PAGE, REGION);

        response.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200) {
                    listener.onFilmsLoaded(true, response.body(), -1, null);
                } else {
                    try {
                        listener.onFilmsLoaded(true, null, response.code(), response.errorBody().string());
                    } catch (IOException e) {
                        listener.onFilmsLoaded(true, null, response.code(), "Generic Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                listener.onFilmsLoaded(false, null, -1, t.getLocalizedMessage());
            }
        });
    }
}
