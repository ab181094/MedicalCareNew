package com.csecu.amrit.medicalcare.models;

/**
 * Created by Amrit on 24/03/2018.
 */

public class Icons {
    int image;
    String name;

    public Icons(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public Icons() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
