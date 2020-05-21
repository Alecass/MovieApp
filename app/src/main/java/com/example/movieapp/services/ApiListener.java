package com.example.movieapp.services;

import com.example.movieapp.models.ApiResponse;

public interface ApiListener {
    void onFilmsLoaded(boolean success, ApiResponse apiResponse, int errorCode, String errorMessage);
}
