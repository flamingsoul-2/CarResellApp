package com.kir4agi.caradvisorapp.model;


import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import com.kir4agi.caradvisorapp.controller.LoadImage;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class Car implements Serializable {

    private transient Bitmap carImage;
    private URL imageURL;
    private String carName;
    private String chassis;
    private String years;
    private boolean active;
    private String brand,model;


    public Car(String carName, Bitmap carImage, String years, boolean active){
        this.carImage = carImage;
        this.carName = carName;
        this.years = years;
        this.active = active;
    }
    public Car(String carName, Bitmap carImage) {
        this.carImage = carImage;
        this.carName = carName;
    }


    public Car(){}

    public void setImage(Bitmap carImage) {
        this.carImage = carImage;
    }

    public void setName(String carName) {
        this.carName = carName;
    }

    public void setYears(String years, boolean active) {
        this.years = years;
        this.active = active;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public Bitmap getCarImage() {

        return carImage;
    }


    public String getCarName() {
        if(carName == null) {
            try {
                throwInitException();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return carName;
    }

    public String getChassis() {
        return chassis;
    }

    public String getYears() {
        return years;
    }

    public boolean isActive() {
        return active;
    }

    private void throwInitException() throws Exception{
        throw new Exception("Car was not initialized");
    }
    @Override
    public String toString() {
        return super.toString() + " " + carName + " in production:" + active + " " + years;
    }

    @Override
    public int hashCode() {
        return this.getCarName().hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(this == obj){
            return true;
        }
        if (obj instanceof Car) {
            Car toCheck = (Car) obj;
            return this.getCarName().equals(toCheck.getCarName());
        }
        return false;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public void setImageURL(URL imageURL) {
        this.imageURL = imageURL;
    }

    public URL getImageURL() {
        return imageURL;
    }

    public void loadImage(){
        if(this.carImage == null){
            LoadImage loadImage = new LoadImage(imageURL);
            Thread loadImageThread = new Thread(loadImage);
            loadImageThread.start();
            try {
                loadImageThread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            this.carImage = loadImage.getImage();
        }
    }


}
