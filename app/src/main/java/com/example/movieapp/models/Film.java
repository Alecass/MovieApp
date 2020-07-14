package com.example.movieapp.models;

import com.google.gson.annotations.SerializedName;

public class Film {
    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String imagePath;

    @SerializedName("overview")
    private String description;

    @SerializedName("vote_average")
    private Float rating;

    private Boolean isFavourite;

    public Film(String title, String imagePath, String description, Boolean isFavourite) {
        this.title = title;
        this.imagePath = imagePath;
        this.description = description;
        this.isFavourite = isFavourite;
        this.rating = rating;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

}
