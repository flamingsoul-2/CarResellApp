package com.kir4agi.caradvisorapp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.kir4agi.caradvisorapp.controller.HTMLModelParser;
import com.kir4agi.caradvisorapp.model.Model;

import java.util.ArrayList;


public class ActivityStarter{

    private static Thread loadingThread;
    private Intent intent;
    private Context context;

    private ActivityStarter(Context context, Intent intent){
        this.context = context;
        this.intent = intent;
    }

    public static void bringCollection(Context context){
        Intent intent = new Intent(context,CollectionActivity.class);
        context.startActivity(intent);
    }
    public static void bringActivity(Context context, final Intent intent)
    {
       context.startActivity(intent);
    }

}
