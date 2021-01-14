package com.kir4agi.caradvisorapp.model;

import android.graphics.Bitmap;

import org.jsoup.nodes.Document;

public class Brand {

    private Bitmap image;
    private String name;
    private String modelsURL;

    private String modifiedName;
    private String modifiedURL;

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public String getModifiedURL() {
        return modifiedURL;
    }

    public void setModifiedURL(String modifiedURL) {
        this.modifiedURL = modifiedURL;
    }

    public String getModelsURL() {
        return modelsURL;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModelsURL(String modelsURL) {
        this.modelsURL = modelsURL;
    }
}
