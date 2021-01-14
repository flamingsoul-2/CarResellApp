package com.kir4agi.caradvisorapp.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;
import com.kir4agi.caradvisorapp.R;
import com.kir4agi.caradvisorapp.model.Car;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CollectionActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoadingScreen.visible = false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        init();

    }

    private void init() {
        ArrayList<Car> savedCars = getSavedCars(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        ConstraintLayout constraintLayout = findViewById(R.id.constrained_layout_2);
        layoutInflater.inflate(R.layout.action_bar_4, constraintLayout);
        ListView listView = findViewById(R.id.list_view_2);
        listView.setAdapter(new ListViewCar(this, R.layout.car_buble, savedCars, this));
    }


    private ArrayList<Car> getSavedCars(Context context) {
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("collection", MODE_PRIVATE);
        ArrayList<Car> temp = new ArrayList<>();
        Gson gson = new Gson();
        Map<String, ?> map = preferences.getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            System.out.println(entry.getValue());
            temp.add(gson.fromJson(entry.getValue().toString(), Car.class));
        }
        return temp;
    }

    public void proceed(Car car) {
        Intent intent = new Intent(this, CarDataActivity.class);
        intent.putExtra("car", car);
        ActivityStarter.bringActivity(this, intent);
    }


}