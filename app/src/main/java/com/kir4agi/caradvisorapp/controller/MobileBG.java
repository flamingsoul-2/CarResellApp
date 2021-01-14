package com.kir4agi.caradvisorapp.controller;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.kir4agi.caradvisorapp.model.Graph;
import com.kir4agi.caradvisorapp.model.Model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MobileBG {

    private static final String url = "https://www.mobile.bg/pcgi/mobile.cgi";
    private Document document;

    Graph graph;
    private int sumOfPrices;


    public MobileBG(String brand, String model, Graph graph) throws Exception{

        this.graph = graph;

        sumOfPrices = 0;

        translate();

        getDocument(brand, model);
        if(document == null){
            throw new Exception("Could not post to " + url);
        }

        extractData(1);
    }

    private void getDocument(final String brand, final String model) throws InterruptedException {
        Thread getDocumentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    document = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                            .data("act", "3")
                            .data("aksess", "")
                            .data("bcgears", "")
                            .data("bcmarka", "")
                            .data("bctype", "")
                            .data("engine_t", "")
                            .data("extinfo", "")
                            .data("location", "")
                            .data("locationc" , "")
                            .data("marka", brand)
                            .data("model", model)
                            .data("nup", "01")
                            .data("partelem", "")
                            .data("price1", "")
                            .data("pubtype", "1")
                            .data("rub", "1")
                            .data("sort", "1")
                            .data("topmenu", "1")
                            .data("transmis", "")
                            .data("twrubr", "")
                            .data("year", "")
                            .post();
                }catch (
                        IOException e
                ){
                    e.printStackTrace();
                }

            }
        });

        getDocumentThread.start();
        getDocumentThread.join();
    }


    private void extractData(int pageNum) throws Exception
    {
        // take all cars

        Elements elements = document.select("span.price");


        for (int i = 0; i < elements.size(); i++) {
            System.out.println(elements.get(i).text());

            takeInfo(elements.get(i).text());
        }
        elements = document.select("a.pageNumbers");

        if(elements.size() - 1 > pageNum) {
            String nextPageURL = "https:" + elements.get(pageNum + 1).attr("href");
            nextPage(nextPageURL);
            extractData(pageNum + 1);
        }

        graph.setSumOfPrices(sumOfPrices);

    }

    private void takeInfo(String info){
        info = info.replace("лв.", "");
        info = info.replace(" ", "");
        info = info.replace("EUR", "");

        if(!info.contains("Договаряне")) {
            int price = Integer.parseInt(info);


            graph.incrementNumberOfAds();

            graph.addPrice(price);
            sumOfPrices += price;
        }
    }


    private void nextPage(final String url) throws Exception{
        Thread getDocumentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    document = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                            .get();
                }catch (
                        IOException e
                ){
                    e.printStackTrace();
                }

            }
        });
        getDocumentThread.start();
        getDocumentThread.join();
    }

    private void translate(){
        // This is done not to have any mistakes when scraping data
        if(this.graph.getBrand().equals("Volkswagen")){
            this.graph.setBrand("VW");
        }
        if(this.graph.getBrand().equals("MG")){
            this.graph.setBrand("Mg");
        }
    }
}
