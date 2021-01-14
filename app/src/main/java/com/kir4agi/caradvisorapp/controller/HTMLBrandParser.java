package com.kir4agi.caradvisorapp.controller;

import android.graphics.Bitmap;

import com.kir4agi.caradvisorapp.model.Brand;
import com.kir4agi.caradvisorapp.model.Model;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HTMLBrandParser {

    ArrayList<Brand> brands;
    private Document document;


    public HTMLBrandParser(){
        document = HTTPRequest.getDocumentHTML("https://auto-data.net/en/");
        brands = new ArrayList<>();
        loadBrands();
    }

    private void loadBrands()
    {
        Elements brandElements = document.select("a.marki_blok");
        for (int i = 0; i < brandElements.size(); i++) {
            brands.add(new Brand());
            this.lastBrandAdded().setImage(loadImage(brandElements, i));
            brands.get(i).setName(getBrandName(brandElements, i));
            brands.get(i).setModelsURL(getModelURL(brandElements, i));

        }

        removeLast();
    }


    // Removes the last element, because it is not a brand element
    private void removeLast() {
        brands.remove(brands.size() - 1);
    }

    private String getModelURL(Elements brandElements, int i) {
        return brandElements.get(i).attr("href");
    }

    private String getBrandName(Elements brandElements, int i) {
        return brandElements.get(i).text();
    }

    private Bitmap loadImage(Elements brandElements, int i) {


        URL url = getImageUrl(brandElements, i);

        LoadImage loadImage = new LoadImage(url);

        Thread thread = new Thread(loadImage);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return loadImage.getImage();

    }

    private URL getImageUrl(Elements brandElements, int i) {
        URL url = null;
        try {
            url = new URL(brandElements.get(i).select("img").get(0).absUrl("src"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public Brand getBrand(int toReturn){
        return brands.get(toReturn);
    }

    private Brand lastBrandAdded()
    {
        return this.brands.get(brands.size() - 1);
    }

    public ArrayList<Brand> getAllBrandsArrayList(){

        return  brands;

    }


}
