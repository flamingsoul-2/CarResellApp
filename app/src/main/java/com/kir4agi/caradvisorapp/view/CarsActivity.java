package com.kir4agi.caradvisorapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kir4agi.caradvisorapp.R;
import com.kir4agi.caradvisorapp.controller.HTMLCarParser;
import com.kir4agi.caradvisorapp.model.Car;

import java.util.ArrayList;

public class CarsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);
        init();

    }

    private void init() {
        Bundle info = getIntent().getExtras();

        LayoutInflater layoutInflater = this.getLayoutInflater();
        ConstraintLayout constraintLayout = findViewById(R.id.constrained_layout_2);
        View actionbar = layoutInflater.inflate(R.layout.action_bar_2,constraintLayout);

        ImageView imageView = actionbar.findViewById(R.id.imageView4);
        imageView.setImageBitmap((Bitmap)info.get("image"));

        HTMLCarParser carsParser = new HTMLCarParser(info.getString("url"), info.getString("brand"), info.getString("model"));
        ArrayList<Car> cars = carsParser.getCars();
        Log.i("carParser", cars.size() + "");

        TextView textView = findViewById(R.id.textView2);
        textView.setText(info.getString("model"));

        ListView listView = findViewById(R.id.list_view_2);
        listView.setAdapter(new ListViewCar(this, R.layout.car_buble,cars,this));


        ImageView collectionImage = findViewById(R.id.imageView_hearth);
        collectionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStarter.bringCollection(CarsActivity.this);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoadingScreen.visible = false;
    }
}
