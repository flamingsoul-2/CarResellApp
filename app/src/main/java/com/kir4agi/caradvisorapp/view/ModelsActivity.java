package com.kir4agi.caradvisorapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.kir4agi.caradvisorapp.R;
import com.kir4agi.caradvisorapp.controller.HTMLModelParser;
import com.kir4agi.caradvisorapp.model.Model;

import java.util.ArrayList;

public class ModelsActivity extends AppCompatActivity {

    private String brandName;
    static ListViewModel adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models);
        init();
    }

    private void init() {
        Bundle info = getIntent().getExtras();
        brandName = info.getString("brand");
        LoadingScreen.visible = false;
        LayoutInflater layoutInflater = this.getLayoutInflater();
        ConstraintLayout constraintLayout = findViewById(R.id.constrained_layout_2);
        View actionbar = layoutInflater.inflate(R.layout.action_bar_2,constraintLayout);
        ImageView imageView = actionbar.findViewById(R.id.imageView4);
        imageView.setImageBitmap((Bitmap)info.get("image"));
        HTMLModelParser modelsParser = new HTMLModelParser(info.getString("URL"), (Bitmap)info.get("image"));
        ArrayList<Model> models = modelsParser.getModels();
        ListView listView = findViewById(R.id.list_view_2);
        adapter = new ListViewModel(this, R.layout.model_buble, models, this);
        listView.setAdapter(adapter);
        ImageView collectionImage = findViewById(R.id.imageView_hearth);
        collectionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStarter.bringCollection(ModelsActivity.this);
            }
        });
    }


    public void proceed(String url, Bitmap bitmap ,String modelName){


        Intent intent = new Intent(this, CarsActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("image", bitmap);
        intent.putExtra("model", modelName);
        intent.putExtra("brand", brandName);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoadingScreen.visible = false;
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.pop_menu, menu);
        ArrayList<Model> modelsRef = adapter.getModels();
        menu.setHeaderTitle("Choose your option for " + modelsRef.get(adapter.getSelectedItemPos()).getModelName());


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.delete_item){
            adapter.getModels().remove(adapter.getSelectedItemPos());
            adapter.notifyDataSetChanged();
        }

        if(item.getItemId() == R.id.edit_item)
        {
            Intent editIntent = new Intent(this, EditView.class);
            Gson gson = new Gson();
            editIntent.putExtra("model", gson.toJson(adapter.getSelectedModel()));
            editIntent.putExtra("pos", adapter.getSelectedItemPos());

            ActivityStarter.bringActivity(this,editIntent);

        }

        return super.onContextItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void updateBrand(int position, String name, String url){
        adapter.updateModel(position, name, url);
        adapter.notifyDataSetChanged();
    }
}
