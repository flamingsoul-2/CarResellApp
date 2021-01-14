package com.kir4agi.caradvisorapp.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kir4agi.caradvisorapp.R;
import com.kir4agi.caradvisorapp.model.Brand;
import com.kir4agi.caradvisorapp.model.Model;

import java.util.ArrayList;

public class ListViewModel extends ArrayAdapter<Model> {

    private ArrayList<Model> models;
    private int resourceLayout;
    private LayoutInflater layoutInflater;
    ModelsActivity modelsActivity;
    private int menuSelectedItemPos;
    private View listItem;



    public ListViewModel(@NonNull Context context, int resource, @NonNull ArrayList<Model> models, ModelsActivity modelsActivity) {
        super(context, resource, models);
        layoutInflater = LayoutInflater.from(context);
        resourceLayout = resource;
        this.models= models;
        this.modelsActivity = modelsActivity;


    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        listItem = convertView;

        if(listItem == null){
            listItem = layoutInflater.inflate(resourceLayout,parent,false);
        }

        listItem.setBackgroundColor(Color.rgb(255,255,255));
        TextView textView = listItem.findViewById(R.id.textview7);
        textView.setText(models.get(position).getModelName());
        ImageView imageView = listItem.findViewById(R.id.imageView11);
        System.out.println(models.get(position).getModelImage());
        imageView.setImageBitmap(models.get(position).getModelImage());

        Button button = listItem.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingScreen.visible = true;
                Model selectedModel = models.get(position);
                modelsActivity.proceed(selectedModel.getCarURL(), selectedModel.getBrandLogo(), selectedModel.getModelName());
            }
        });

        modelsActivity.registerForContextMenu(listItem);
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuSelectedItemPos = position;
                modelsActivity.openContextMenu(listItem);
                return true;
            }
        });


        return listItem;
    }

    public void updateModel(int position, String name, String url)
    {
        this.models.get(position).setCarURL(url);
        this.models.get(position).setModelName(name);

    }

    public ArrayList<Model> getModels() {
        return models;
    }

    public Model getSelectedModel(){
        return getModels().get(getSelectedItemPos());
    }

    public int getSelectedItemPos() {
        return menuSelectedItemPos;
    }

}
