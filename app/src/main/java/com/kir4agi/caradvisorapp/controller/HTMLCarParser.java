package com.kir4agi.caradvisorapp.controller;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.kir4agi.caradvisorapp.model.Car;
import com.kir4agi.caradvisorapp.view.LoadingScreen;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HTMLCarParser {

    private ArrayList<Car> cars;
    private Document document;
    private final String beginURL = "https://www.auto-data.net";
    private String brand,model;

    public HTMLCarParser(String url, String brand, String model)
    {
        this.brand = brand;
        this.model = model;
        document = HTTPRequest.getDocumentHTML(beginURL + url);
        Elements carElements = document.select("th.i");
        cars = new ArrayList<>();
        load(carElements);

    }

    private void load(Elements imageElements){
        for (int i = 0; i < imageElements.size(); i++) {
            cars.add(new Car());
            loadImages(imageElements, i);
            loadName(i);
            loadChassis(i);
            loadYears(i);
            setBrandModel(i);
        }
    }

    private void setBrandModel(int i){
        cars.get(i).setBrand(this.brand);
        cars.get(i).setModel(this.model);
    }

    private void loadImages(Elements imageElements, int i){

        URL url = null;
        try{
            url = new URL(imageElements.get(i).select("img").get(0).absUrl("src"));
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        if(url == null){ throw new NullPointerException("URL is null. Selection Failed");
        }

        LoadImage loadImage = new LoadImage(url);
        Thread thread = new Thread(loadImage);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) { e.printStackTrace(); }

        this.getLast().setImage(loadImage.getImage());
        this.getLast().setImageURL(url);

    }

    private void loadName(int i){
        Elements elements = document.select(".tit");

        this.getLast().setName(elements.get(i).text());

    }

    private void loadChassis(int i){
        Elements elements = document.select("strong.chas");
        try {
            this.getLast().setChassis(elements.get(i).text());
        }catch (IndexOutOfBoundsException e){
            Log.i("HTTP REQUEST: ", " could not load info for car " + this.getLast().getCarName() + " loadChassis();");
            this.getLast().setChassis("N/A");
        }
    }

    private void loadYears(int i) {
        Elements elements = document.select("strong.cur");


        try {
            if (elements.size() != 0) {
                this.getLast().setYears(elements.get(i).text(), true);
            } else {
                elements = document.select("strong.end");
                if (elements.size() != 0) {
                    this.getLast().setYears(elements.get(i).text(), false);
                }
            }
        }catch (IndexOutOfBoundsException e){
            Log.i("HTTP REQUEST: ", " could not load info for car " + this.getLast().getCarName() + " loadYears();");
            this.getLast().setYears("N/A", false);
        }

    }



    private Car getLast(){
        return cars.get(cars.size() - 1);
    }

    public Document getDocument() {
        return document;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }
}
