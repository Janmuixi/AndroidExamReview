package com.example.jan.listimagesexamandroid.Models;

public class FirebaseImageModel {
    String date;
    String imagesUrl;

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

    public FirebaseImageModel(String date, String imagesUrl) {
        this.date = date;
        this.imagesUrl = imagesUrl;
    }
}
