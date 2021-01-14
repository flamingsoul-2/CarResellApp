package com.kir4agi.caradvisorapp.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class LoadImage implements Runnable {



    private ArrayList<Bitmap> imagesbuf;
    private Bitmap[] imagesArr;
    private Bitmap image;



    private URL url;

    public LoadImage(ArrayList<Bitmap> images, URL url){
        this.url = url;
        this.imagesbuf = images;
    }

    LoadImage(Bitmap[] images, URL url){
        this.url = url;
        this.imagesArr = images;
    }

    LoadImage(Bitmap images, URL url){
        this.url = url;
        this.image = images;
    }


     public LoadImage(URL url){
        this.url = url;
    }




    @Override
    public void run() {
        if(imagesbuf != null){
            imagesbuf.add(readImage(url));
        } else if(imagesArr != null){
            addImageToArray(readImage(url));
        }else{
            image = readImage(url);
        }
    }

    private Bitmap readImage(String u){
            Bitmap bmp = null;
            InputStream inputStream = null;
            try {
                URL url = new URL(u);
                inputStream = url.openConnection().getInputStream();
                bmp = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(inputStream != null){
                    try {
                        inputStream.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        return bmp;
    }
    private Bitmap readImage(URL url){
        return readImage(url.toExternalForm());
    }

    private void addImageToArray(Bitmap toLoad) {
        byte nextFree = -1;

        // search for next free position
        for (byte i = 0; i < imagesArr.length; i++) {
            if(imagesArr[i] == null){
                nextFree = i;
                break;
            }
        }

        imagesArr[nextFree] = toLoad;

    }

    public Bitmap getImage() {
        return image;
    }

}
