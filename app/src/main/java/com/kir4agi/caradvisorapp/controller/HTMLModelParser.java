package com.kir4agi.caradvisorapp.controller;

import android.graphics.Bitmap;
import android.view.Display;


import com.kir4agi.caradvisorapp.model.Model;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HTMLModelParser {
    private Document document;
    ArrayList<Model> models;

    public HTMLModelParser(String url, Bitmap brandLogo) {
        document = HTTPRequest.getDocumentHTML("https://auto-data.net/" + url);
        models = new ArrayList<>();
        loadModels();
        setLogo(brandLogo);
    }

    private void setLogo(Bitmap brandLogo) {
        for(Model m : models){
            m.setBrandLogo(brandLogo);
        }
    }


    public HTMLModelParser(URL url) {
        document = HTTPRequest.getDocumentHTML(url);
        models = new ArrayList<>();
    }


    private void loadModels() {
        Elements elements = document.select("a.modeli");

        for (int i = 0; i < elements.size(); i++) {
            models.add(new Model());
            this.lastModelAdded().setCarURL(loadCarURLs(elements, i));
            this.lastModelAdded().setModelImage(loadImage(elements,i));
            this.lastModelAdded().setModelName(loadName(elements,i));

        }
    }

    private String loadCarURLs(Elements elements, int i) {
        return elements.get(i).attr("href");
    }


    private Bitmap loadImage(Elements elements,int i) {
        URL url = getImageUrl(elements, i);
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

    private URL getImageUrl(Elements modelElements, int i) {
        URL url = null;
        try {
            url = new URL(modelElements.get(i).select("img").get(0).absUrl("src"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String loadName(Elements elements, int i) {
        return elements.get(i).text();
    }

    private Model lastModelAdded()
    {
        return this.models.get(models.size() - 1);
    }

    public ArrayList<Model> getModels() {
        return models;
    }
}
