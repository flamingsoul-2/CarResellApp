package com.kir4agi.caradvisorapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.kir4agi.caradvisorapp.R;
import com.kir4agi.caradvisorapp.model.Car;
import com.kir4agi.caradvisorapp.model.Graph;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CarDataActivity extends AppCompatActivity {

    private Car car;
    private ImageView hearthImage;
    private  TextView collectionTextView;
    private Graph carDetails;
    private ArrayList<String> carDetailsString;
    private ListView listView;
    private  LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_data);

        LayoutInflater layoutInflater = this.getLayoutInflater();
        ConstraintLayout constraintLayout = findViewById(R.id.constrained_layout_3);
        layoutInflater.inflate(R.layout.action_bar_3,constraintLayout);

        Bundle info = getIntent().getExtras();

        hearthImage = findViewById(R.id.imageView_hearth);
        collectionTextView = findViewById(R.id.textView3);



        try {
            car = (Car) info.get("car");
            setCaption();
            hearthImage = findViewById(R.id.imageView_hearth);
            hearthImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveCar(getApplicationContext(), CarDataActivity.this.car);
                    if(inPreferences(car)){
                        CarDataActivity.this.hearthImage.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.iconmonstr_heart_thin_filled));
                        collectionTextView.setText(R.string.remove);
                    }else{
                        CarDataActivity.this.hearthImage.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.iconmonstr_heart_thin));
                        collectionTextView.setText(R.string.add_favorites);
                    }
                }
            });
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        if(inPreferences(car)){
            collectionTextView.setText(R.string.remove);
            hearthImage.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.iconmonstr_heart_thin_filled));

        }
        lineChart = findViewById(R.id.line_chart);
        lineChart.setNoDataText("Loading...");

        if(car != null) {
            carDetails = new Graph(car.getBrand(), car.getModel() , lineChart, this);
            carDetails.loadData();

        }
        carDetailsString = null;
        carDetailsString = new ArrayList<>();
        carDetailsString.add("Average price: " + "N/a");
        carDetailsString.add("Median price: " + "N/a");
        carDetailsString.add("Standart deviation: " +"N/a");
        carDetailsString.add("Number of ads: " + "N/a");
        carDetailsString.add("Popularity: " + "N/a");
        listView = findViewById(R.id.list_view_car_details);
        listView.setAdapter(new ListViewDetails(this, R.layout.car_details, carDetailsString));
    }

    private void setCaption() {
        TextView textView = findViewById(R.id.textView2);
        String carName = car.getCarName();
        if(carName.length() > 20){
            textView.setTextSize(12);
        }else{
            textView.setTextSize(18);
        }
        textView.setText(car.getCarName());
    }

    public void loadDetails(Graph graph){
        carDetailsString = graph.getDetails();
        listView = findViewById(R.id.list_view_car_details);
        listView.setAdapter(new ListViewDetails(this, R.layout.car_details, carDetailsString));
        ArrayList<Entry> entries = graph.getEntries();
        LineDataSet lineDataSet = new LineDataSet(entries, "Car");
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.GREEN);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setFillAlpha(125);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        LineData data = new LineData(lineDataSet);
        this.lineChart.setData(data);
        this.lineChart.getLegend().setEnabled(false);
        this.lineChart.getDescription().setEnabled(false);
        if(entries.size() < 3)
        {
            this.lineChart.getDescription().setEnabled(true);
            this.lineChart.getDescription().setTextColor(Color.RED);
            this.lineChart.getDescription().setTextSize(12f);
            this.lineChart.getDescription().setText("Cannot draw graph - not enough info");
        }
        this.lineChart.invalidate();


    }

    private void saveCar(Context context, Car car){
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("collection", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = preferences.edit();
        String carJson = gson.toJson(car);

        String hashCode = carJson.hashCode() + "";
        System.out.println(hashCode);

        if(notInPreferences(preferences, hashCode)){
            editor.putString(hashCode + "", carJson);
            Toast toast = Toast.makeText(context, "Saved to my collection", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            editor.remove(hashCode);
            Toast toast = Toast.makeText(context, "Removed from collection", Toast.LENGTH_SHORT);
            toast.show();
        }
        editor.apply();
    }

    private boolean notInPreferences(SharedPreferences preferences, String hashCode) {
        String inMemory = preferences.getString(hashCode, "");
        return inMemory.equals("");
    }

    private boolean notInPreferences(Car car) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("collection", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String carJson = gson.toJson(car);
        String hashCode = carJson.hashCode() + "";
        String inMemory = preferences.getString(hashCode, "");
        return inMemory.equals("");
    }

    private boolean inPreferences(Car car) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("collection", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String carJson = gson.toJson(car);
        String hashCode = carJson.hashCode() + "";
        String inMemory = preferences.getString(hashCode, "");
        return !inMemory.equals("");
    }
}
