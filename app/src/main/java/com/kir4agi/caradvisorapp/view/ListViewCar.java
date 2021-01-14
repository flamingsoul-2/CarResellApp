package com.kir4agi.caradvisorapp.view;

import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.kir4agi.caradvisorapp.R;
import com.kir4agi.caradvisorapp.model.Car;
import com.kir4agi.caradvisorapp.model.Model;

import java.util.ArrayList;
import java.util.Map;

public class ListViewCar extends ArrayAdapter<Car> {

    private ArrayList<Car> cars;
    private int resourceLayout;
    private LayoutInflater layoutInflater;
    CarsActivity carsActivity;
    CollectionActivity collectionActivity;

    public ListViewCar(@NonNull Context context, int resource, @NonNull ArrayList<Car> car, CarsActivity carsActivity) {
        super(context, resource, car);
        layoutInflater = LayoutInflater.from(context);
        resourceLayout = resource;
        this.cars= car;
        this.carsActivity = carsActivity;
    }

    public ListViewCar(@NonNull Context context, int resource, @NonNull ArrayList<Car> car, CollectionActivity collectionActivity) {
        super(context, resource, car);
        layoutInflater = LayoutInflater.from(context);
        resourceLayout = resource;
        this.cars= car;
        this.collectionActivity = collectionActivity;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {

        View listItem = convertView;

        if(listItem == null){
            listItem = layoutInflater.inflate(resourceLayout,parent,false);
        }

        listItem.setBackgroundColor(Color.rgb(255,255,255));
        TextView textView = listItem.findViewById(R.id.textView4);
        textView.setText(cars.get(position).getCarName());

        TextView textView2 = listItem.findViewById(R.id.textView5);
        textView2.setText(cars.get(position).getYears());
        textView2.setTextColor(makeColor(cars.get(position).isActive()));

        ImageView imageView = listItem.findViewById(R.id.imageView2);
        System.out.println(cars.get(position).getCarImage());

        Bitmap carImage = cars.get(position).getCarImage();

        if(carImage == null){
            cars.get(position).loadImage();
            carImage = cars.get(position).getCarImage();
        }

        imageView.setImageBitmap(carImage);

        Button button = listItem.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), CarDataActivity.class);
                intent.putExtra("car", cars.get(position));
                ActivityStarter.bringActivity(parent.getContext(), intent);
            }
        });


        return listItem;
    }

    private int makeColor(boolean active) {
        return active ? Color.rgb(0,125,0) : Color.rgb(125, 0,0);
    }








}
