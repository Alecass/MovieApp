package com.example.movieapp.models;

public class Film {
    private String title;
    private String imagePath;
    private String description;
    private Boolean isFavourite;

    public Film(String title, String imagePath, String description, Boolean isFavourite) {
        this.title = title;
        this.imagePath = imagePath;
        this.description = description;
        this.isFavourite = isFavourite;
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
