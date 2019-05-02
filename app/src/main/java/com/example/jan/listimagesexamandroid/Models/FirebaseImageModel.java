package com.example.jan.listimagesexamandroid.Models;

public class FirebaseImageModel {
    String date;
    String imagesUrl;
    String description;
    String aula;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public FirebaseImageModel() {}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public FirebaseImageModel(String date, String imagesUrl, String description, String aula) {
        this.date = date;
        this.imagesUrl = imagesUrl;
        this.description = description;
        this.aula = aula;
    }
}

