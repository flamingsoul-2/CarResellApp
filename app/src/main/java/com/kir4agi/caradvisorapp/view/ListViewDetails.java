package com.kir4agi.caradvisorapp.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kir4agi.caradvisorapp.R;
import com.kir4agi.caradvisorapp.model.Brand;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListViewDetails extends ArrayAdapter<String> {

    private LayoutInflater layoutInflater;
    private int resourceLayout;
    private ArrayList<String > details;
    private BrandsActivity activity;

    public ListViewDetails(@NonNull Context context, int resource , ArrayList<String> details) {
        super(context, resource,details);
        layoutInflater = LayoutInflater.from(context);
        resourceLayout = resource;
        this.details = details;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i("ListView", "Called " + position);
        View listItem = convertView;
        if(listItem == null) {
            if(details.get(position).contains("Popularity") && !details.get(position).contains("N/a")){
                listItem = layoutInflater.inflate(R.layout.car_details_popularity, parent, false);
                Popularity popularity = listItem.findViewById(R.id.popularity);
                String popularityScore = details.get(position).replace("Popularity: ", "");
                popularity.setPopularity(Integer.parseInt(popularityScore));
            }else {
                listItem = layoutInflater.inflate(resourceLayout, parent, false);
                TextView textView = listItem.findViewById(R.id.details);
                textView.setText(details.get(position));
                textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            }
        }

        return listItem;
    }
}
