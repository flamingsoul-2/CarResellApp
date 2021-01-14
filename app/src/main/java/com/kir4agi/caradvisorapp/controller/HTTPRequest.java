package com.kir4agi.caradvisorapp.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URL;

public class HTTPRequest implements Runnable{

    private static Document document;
    private String url;



    private HTTPRequest(String url){
        this.url = url;
    }


    static Document getDocumentHTML(URL url){
        String urlStirng = url.toString();
        Thread thread = new Thread(new HTTPRequest(urlStirng));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return document;
    }
    static Document getDocumentHTML(String url){
        Thread thread = new Thread(new HTTPRequest(url));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return document;
    }

    @Override
    public synchronized void run() {

        try {
            document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
