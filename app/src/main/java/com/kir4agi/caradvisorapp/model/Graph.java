package com.kir4agi.caradvisorapp.model;

import android.bluetooth.BluetoothClass;
import android.media.audiofx.DynamicsProcessing;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.kir4agi.caradvisorapp.controller.MobileBG;
import com.kir4agi.caradvisorapp.view.CarDataActivity;
import com.kir4agi.caradvisorapp.view.LoadingScreen;

import java.security.KeyStore;
import java.security.spec.ECField;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Graph implements Runnable{

    private int numberOfAds;
    private int sumOfPrices;
    private ArrayList<Integer> prices;
    private String brand, model;
    private LineChart barChart;
    public Handler handler;
    CarDataActivity carDataActivity;
    private DecimalFormat df;



    public Graph(String brand, String model, LineChart barChart){
        this.brand = brand;
        this.model = model;
        prices = new ArrayList<>();
        this.barChart = barChart;
        df = new DecimalFormat("###,###,###");


    }

    public Graph(String brand, String model, LineChart barChart, CarDataActivity activity){
        this.brand = brand;
        this.model = model;
        prices = new ArrayList<>();
        this.barChart = barChart;
        this.carDataActivity = activity;
        df = new DecimalFormat("###,###,###");


    }

    public void loadData(){
        Thread thread = new Thread(this);
        thread.start();
    }

    public ArrayList<Entry> getEntries(){

        ArrayList<Entry> toReturn = new ArrayList<>();
        ArrayList<Integer> sortedPrices = getSortedPrices();
        if(sortedPrices.size() > 2) {
            for (int i = 0; i < sortedPrices.size(); i++) {
                int numbers = (int)Math.floor(Math.log10(sortedPrices.get(i))) + 1;
                int group = (int) (sortedPrices.get(i) / Math.pow(10, numbers - 1));
                toReturn.add(new Entry(prices.get(i), group));
            }
        }


        return toReturn;
    }



    @Override
    public void run() {
        LoadingScreen.visible = true;
        System.out.println("Starts");
        handler = new Handler(carDataActivity.getMainLooper());
        synchronized (this) {
            try {
                MobileBG mobileBG = new MobileBG(Graph.this.brand, Graph.this.model, this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("Finishes+");
        LoadingScreen.visible = false;

        Graph.this.barChart.invalidate();

        Runnable updateDetails = new Runnable() {
            @Override
            public void run() {
                carDataActivity.loadDetails(Graph.this);
            }
        };

        handler.post(updateDetails);

    }

    public ArrayList<String> getDetails(){
        ArrayList<String> details = new ArrayList<>();

        details.add("Average price: " + getAveragePrice());
        details.add("Median price: " + getMedianPrice());
        details.add("Standart deviation: " + calculateSD());
        details.add("Number of ads: " + this.numberOfAds);

        details.add("Popularity: " + getPopularity());

        return details;
    }

    private String getPopularity() {
        int popularity = numberOfAds / 32;

        if(popularity > 10){
            popularity = 10;
        }

        if(popularity == 0){
            popularity = 1;
        }
        return popularity + "";
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setNumberOfAds(int numberOfAds) {
        this.numberOfAds = numberOfAds;
    }

    public void setSumOfPrices(int sumOfPrices) {
        this.sumOfPrices = sumOfPrices;
    }

    public void addPrice(int price){
        this.prices.add(price);
    }

    public ArrayList<Integer> getPrices() {
        return prices;
    }

    public int getNumberOfAds() {
        return numberOfAds;
    }

    public int getSumOfPrices() {
        return sumOfPrices;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public void removePrice(int position){
        this.prices.remove(position);
    }

    public void incrementNumberOfAds(){
        this.numberOfAds++;
    }

    private String getAveragePrice(){
        int average = 0;

        if(this.numberOfAds > 1) {
            for (int i = 0; i < this.numberOfAds; i++) {
                average += this.prices.get(i);
            }
            return df.format((float)(average / this.numberOfAds) * 0.5112) + "€";
        }else{
            return "N/a";
        }
    }

    private String getMedianPrice(){
        if(numberOfAds > 2) {
            ArrayList<Integer> sortedPrices = getSortedPrices();

            int middle = sortedPrices.size() / 2;
            double toReturn = 0;

            if (sortedPrices.size() % 2 == 0) {
                toReturn = sortedPrices.get(middle) * 0.5112;
            } else {
                toReturn = (float)((sortedPrices.get(middle - 1) + sortedPrices.get(middle)) / 2) * 0.5112;

            }

            return df.format(toReturn)  + "€" ;
        }else{
            return "N/a";
        }
    }

    public String calculateSD() {
        if (this.numberOfAds > 0) {
            double sum = 0.0, standardDeviation = 0.0;
            int length = prices.size();

            for (Integer num : prices) {
                sum += num;
            }

            double mean = sum / length;

            for (double num : prices) {
                standardDeviation += Math.pow(num - mean, 2);
            }

            double toReturn = Math.sqrt(standardDeviation / length) * 0.5112;


            return df.format(toReturn)  + "€";
        }else{
            return "N/a";
        }
    }

    private ArrayList<Integer> getSortedPrices(){
        ArrayList<Integer> sortedPrices = prices;
        Collections.sort(sortedPrices);
        return sortedPrices;
    }




    @NonNull
    @Override
    public String toString() {
        return super.toString() + " " + brand + " " + model;
    }



}
