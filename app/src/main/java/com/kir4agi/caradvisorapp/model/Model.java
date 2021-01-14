package com.kir4agi.caradvisorapp.model;

import android.graphics.Bitmap;

import org.jsoup.nodes.Document;

public class Model {

    private Bitmap brandLogo;
    private Bitmap modelImage;
    private String modelName;
    private String brandName;
    private String carURL;

    public Model(){

    }

    public Model(Bitmap modelImage, String brandName ,String modelName, String carURL)
    {
        this.carURL = carURL;
        this.modelImage = modelImage;
        this.brandName = brandName;
        this.modelName = modelName;

    }

    public Bitmap getModelImage() {
        return modelImage;
    }

    public String getCarURL() {
        return carURL;
    }

    public String getModelName() {
        return modelName;
    }

    public void setCarURL(String carURL) {
        this.carURL = carURL;
    }

    public void setModelImage(Bitmap modelImage) {
        this.modelImage = modelImage;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setBrandLogo(Bitmap brandLogo) {
        this.brandLogo = brandLogo;
    }

    public Bitmap getBrandLogo() {
        return brandLogo;
    }



    public String getBrandName() {
        return brandName;
    }
}
