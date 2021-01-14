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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.kir4agi.caradvisorapp.R;
import com.kir4agi.caradvisorapp.model.Brand;

import java.util.ArrayList;

public class ListViewBrand extends ArrayAdapter<Brand> {

    private LayoutInflater layoutInflater;
    private int resourceLayout;
    private ArrayList<Brand> brands;
    private BrandsActivity activity;
    private View listItem;
    private int menuSelectedItemPos;

    public ListViewBrand(@NonNull Context context, int resource , ArrayList<Brand> brands, BrandsActivity activity) {
        super(context, resource,brands);
        layoutInflater = LayoutInflater.from(context);
        resourceLayout = resource;
        this.brands = brands;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Log.i("ListView", "Called " + position);

        if(position == 1){
            System.out.println(brands.get(position));
        }

        listItem = convertView;

        if(listItem == null) {
            listItem = layoutInflater.inflate(resourceLayout, parent, false);
        }
        listItem.setBackgroundColor(Color.rgb(255,255,255));
        TextView textView = listItem.findViewById(R.id.textview7);
        textView.setText(brands.get(position).getName());
        ImageView imageView = listItem.findViewById(R.id.imageView11);
        imageView.setImageBitmap(brands.get(position).getImage());

        Button button = listItem.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!LoadingScreen.visible) {
                    LoadingScreen.visible = true;
                    activity.proceed(brands.get(position).getModelsURL(), brands.get(position).getName() ,brands.get(position).getImage());
                }

            }
        });


        activity.registerForContextMenu(listItem);
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuSelectedItemPos = position;
                activity.openContextMenu(listItem);
                return true;
            }
        });

        return listItem;
    }

    public void updateBrand(int position, String name, String url)
    {
        this.brands.get(position).setModelsURL(url);
        this.brands.get(position).setName(name);

    }

    public ArrayList<Brand> getBrands() {
        return brands;
    }

    public Brand getSelectedBrand(){
        return getBrands().get(getSelectedItemPos());
    }

    public int getSelectedItemPos() {
        return menuSelectedItemPos;
    }
}
